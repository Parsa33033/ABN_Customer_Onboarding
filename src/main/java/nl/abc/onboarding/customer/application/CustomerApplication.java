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

    /**
     * Onboard a customer with photo and id document using these steps:
     *
     * 1. Map the request to application data structure and generate an
     * internal identifier (UUID)
     * 2. Store the photo and save the path in the application data
     * 3. Store the id document and save the path in the application data
     * 4. complete the CustomerData DTO with the photo and id document paths,
     * and UUID identifier
     * 4. Onboard the customer using the customer service
     *
     * @param request
     * @param photo
     * @param idDocument
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @PostMapping(value = "/onboarding", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CustomerResponse> onboard(
            @RequestPart("data") CustomerRequest request,
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("id-document") MultipartFile idDocument
    ) throws ExecutionException, InterruptedException {

       CustomerResponse customerResponse =
         mapRequestAndGenerateInternalIdentifier(request, photo, idDocument)
            .thenCompose(this::storePhotoAndSaveThePath)
            .thenCompose(this::storeIdDocumentAndSaveThePath)
            .thenCompose(i -> customerService.onboard(i.customerData))
            .thenApply(CustomerResponse::from).get();

        return ResponseEntity.ok(customerResponse);
    }

    /**
     * Map the request to application data structure and generate an internal
     * identifier (UUID) for file storage purposes.
     * @param request
     * @param photo
     * @param idDocument
     * @return
     */
    private static CompletableFuture<CustomerApplicationData> mapRequestAndGenerateInternalIdentifier(
            CustomerRequest request, MultipartFile photo, MultipartFile idDocument) {
        CustomerApplicationData data =
                new CustomerApplicationData(request.toDataTransferObject());
        data.setPhoto(photo);
        data.setIdDocument(idDocument);
        return CompletableFuture.completedFuture(data);
    }

    /**
     * Store the photo and save the path in the application data
     * @param data
     * @return
     */
    private CompletableFuture<CustomerApplicationData> storePhotoAndSaveThePath(
            CustomerApplicationData data) {
        if (data.photo == null) {
            return CompletableFuture.completedFuture(data);
        }
        return fileStorage.persist(data.photo, data.externalIdentifier,
                             FileType.PHOTO)
                .thenApply(data::setPhotoPath);
    }


    /**
     * Store the id document and save the path in the application data
     * @param data
     * @return
     */
    private CompletableFuture<CustomerApplicationData> storeIdDocumentAndSaveThePath(
            CustomerApplicationData data) {
        if (data.idDocument == null) {
            return CompletableFuture.completedFuture(data);
        }
        return fileStorage.persist(data.idDocument, data.externalIdentifier,
                                   FileType.ID)
                .thenApply(data::setIdDocumentPath);
    }
}
