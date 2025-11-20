package nl.abc.onboarding.customer.domain.ports.dtos;

import nl.abc.onboarding.customer.framework.DataTransferObject;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerData(
        UUID identifier,
        String externalIdentifier,
        String firstName,
        String lastName,
        String gender,
        LocalDate dateOfBirth,
        String phoneNumber,
        String email,
        String nationality,
        AddressData residentialAddress,
        String socialSecurityNumber,
        String idDocumentPath,
        String photoPath
) implements DataTransferObject {

    public CustomerData withPhotoPath(String photoPath) {
        return new CustomerData(
                this.identifier,
                this.externalIdentifier,
                this.firstName,
                this.lastName,
                this.gender,
                this.dateOfBirth,
                this.phoneNumber,
                this.email,
                this.nationality,
                this.residentialAddress,
                this.socialSecurityNumber,
                this.idDocumentPath,
                photoPath
        );
    }

    public CustomerData withIdDocumentPath(String idDocumentPath) {
        return new CustomerData(
                this.identifier,
                this.externalIdentifier,
                this.firstName,
                this.lastName,
                this.gender,
                this.dateOfBirth,
                this.phoneNumber,
                this.email,
                this.nationality,
                this.residentialAddress,
                this.socialSecurityNumber,
                idDocumentPath,
                this.photoPath
        );
    }

    public CustomerData withIdentifier(UUID identifier) {
        return new CustomerData(
                identifier,
                this.externalIdentifier,
                this.firstName,
                this.lastName,
                this.gender,
                this.dateOfBirth,
                this.phoneNumber,
                this.email,
                this.nationality,
                this.residentialAddress,
                this.socialSecurityNumber,
                this.idDocumentPath,
                this.photoPath
        );
    }

}
