package nl.abc.onboarding.customer.application;

import lombok.Getter;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class CustomerApplicationData {
    String externalIdentifier;
    MultipartFile photo;
    MultipartFile idDocument;

    CustomerData customerData;

    String photoPath;
    String idDocumentPath;

    /**
    generation of the UUID identifier for the customer is done here
     */
    public CustomerApplicationData(CustomerData customerData) {
        this.customerData = customerData;
        this.externalIdentifier = customerData.externalIdentifier();
        this.customerData.withIdentifier(UUID.randomUUID());
    }

    public CustomerApplicationData setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        this.customerData.withPhotoPath(photoPath);
        return this;
    }

    public CustomerApplicationData setIdDocumentPath(String idDocumentPath) {
        this.idDocumentPath = idDocumentPath;
        this.customerData.withIdDocumentPath(idDocumentPath);
        return this;
    }
}
