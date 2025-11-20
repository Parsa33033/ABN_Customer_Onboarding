package nl.abc.onboarding.customer.application;

import nl.abc.onboarding.customer.application.response.CustomerRequest;
import nl.abc.onboarding.customer.application.response.CustomerResponse;
import nl.abc.onboarding.customer.domain.ports.incoming.CustomerService;
import nl.abc.onboarding.customer.infrastructure.filestorage.FileStorage;
import nl.abc.onboarding.customer.infrastructure.filestorage.FileType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class CustomerApplication {

    public final CustomerService customerService;
    public final FileStorage fileStorage;

    public CustomerApplication(CustomerService customerService,
                               FileStorage fileStorage) {
        this.customerService = customerService;
        this.fileStorage = fileStorage;
    }

    @PostMapping(value = "/onboarding", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CustomerResponse> onboard(
            @RequestPart("data") CustomerRequest request,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("id-document") MultipartFile idDocument
    ) throws ExecutionException, InterruptedException {

       CustomerResponse customerResponse =
         mapRequestAndGenerateInternalIdentifier(request)
            .thenCompose(this::storePhoto)
            .thenCompose(this::storeIdDocument)
            .thenCompose(i -> customerService.onboard(i.customerData))
            .thenApply(CustomerResponse::from).get();

        return ResponseEntity.ok(customerResponse);
    }

    private static CompletableFuture<CustomerApplicationData> mapRequestAndGenerateInternalIdentifier(
            CustomerRequest request) {
        return CompletableFuture.completedFuture(
                new CustomerApplicationData(request.toDataTransferObject()));
    }

    private CompletableFuture<CustomerApplicationData> storePhoto(CustomerApplicationData data) {
        return fileStorage.persist(data.photo, data.externalIdentifier,
                             FileType.PHOTO)
                .thenApply(data::setPhotoPath);
    }

    private CompletableFuture<CustomerApplicationData> storeIdDocument(CustomerApplicationData data) {
        return fileStorage.persist(data.idDocument, data.externalIdentifier,
                                   FileType.ID)
                .thenApply(data::setIdDocumentPath);
    }
}
