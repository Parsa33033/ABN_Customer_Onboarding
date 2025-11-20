package nl.abc.onboarding.customer.application.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {

    @Size(max = 120)
    private String externalIdentifier;

    @Size(max = 120)
    private String firstName;

    @Size(max = 120)
    private String lastName;

    @Size(max = 120)
    private String gender;

    private LocalDate dateOfBirth;

    @Size(max = 120)
    private String phoneNumber;

    @Size(max = 120)
    private String email;

    @Size(max = 120)
    private String nationality;

    @Valid
    private AddressRequest residentialAddress;

    @Size(max = 120)
    private String socialSecurityNumber;

    @Size(max = 120)
    private String idDocumentPath;

    @Size(max = 120)
    private String photoPath;

    public CustomerData toDataTransferObject() {
        AddressData addressData = null;
        if (this.residentialAddress != null) {
            addressData = new AddressData(
                    this.residentialAddress.address,
                    this.residentialAddress.zipCode,
                    this.residentialAddress.city,
                    this.residentialAddress.country
            );
        }

        return new CustomerData(
                null,
                this.externalIdentifier,
                this.firstName,
                this.lastName,
                this.gender,
                this.dateOfBirth,
                this.phoneNumber,
                this.email,
                this.nationality,
                addressData,
                this.socialSecurityNumber,
                this.idDocumentPath,
                this.photoPath
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class AddressRequest {

        @Size(max = 120)
        private String address;

        @Size(max = 120)
        private String zipCode;

        @Size(max = 120)
        private String city;

        @Size(max = 120)
        private String country;

    }
}
