package nl.abc.onboarding.customer.domain.ports.entities;

import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.entities.valueobjects.Email;
import nl.abc.onboarding.customer.domain.ports.entities.valueobjects.Gender;
import nl.abc.onboarding.customer.domain.ports.entities.valueobjects.PhoneNumber;
import nl.abc.onboarding.customer.domain.ports.entities.exceptions.ExceptionUtil;
import nl.abc.onboarding.customer.framework.DomainEntity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class CustomerEntity implements DomainEntity<CustomerData> {

    private UUID identifier;
    private String externalIdentifier;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private PhoneNumber phoneNumber;
    private Email email;
    private String nationality;
    private AddressEntity residentialAddress;
    private String socialSecurityNumber;
    private String idDocumentPath;
    private String photoPath;

    private CustomerEntity() {}

    private static Builder builder() {
        return new Builder();
    }

    public static CustomerEntity build() {
        return new CustomerEntity();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.identifier);
    }

    @Override
    public CustomerData toDataTransferObject() {
        return new CustomerData(
                this.identifier,
                this.externalIdentifier,
                this.firstName,
                this.lastName,
                this.gender.getValue(),
                this.dateOfBirth,
                this.phoneNumber.getValue(),
                this.email.getValue(),
                this.nationality,
                this.residentialAddress.toDataTransferObject(),
                this.socialSecurityNumber,
                this.idDocumentPath,
                this.photoPath
        );
    }

    @Override
    public CustomerEntity fromDataTransferObject(CustomerData dataTransferObject) {
        CustomerEntity built = CustomerEntity.builder()
                .identifier(dataTransferObject.identifier())
                .externalIdentifier(dataTransferObject.externalIdentifier())
                .firstName(dataTransferObject.firstName())
                .lastName(dataTransferObject.lastName())
                .gender(dataTransferObject.gender())
                .dateOfBirth(dataTransferObject.dateOfBirth())
                .phoneNumber(dataTransferObject.phoneNumber())
                .email(dataTransferObject.email())
                .nationality(dataTransferObject.nationality())
                .residentialAddress(dataTransferObject.residentialAddress())
                .socialSecurityNumber(dataTransferObject.socialSecurityNumber())
                .idDocumentPath(dataTransferObject.idDocumentPath())
                .photoPath(dataTransferObject.photoPath())
                .build();

        // copy built values into this instance
        this.identifier = built.identifier;
        this.externalIdentifier = built.externalIdentifier;
        this.firstName = built.firstName;
        this.lastName = built.lastName;
        this.gender = built.gender;
        this.dateOfBirth = built.dateOfBirth;
        this.phoneNumber = built.phoneNumber;
        this.email = built.email;
        this.nationality = built.nationality;
        this.residentialAddress = built.residentialAddress;
        this.socialSecurityNumber = built.socialSecurityNumber;
        this.idDocumentPath = built.idDocumentPath;
        this.photoPath = built.photoPath;

        return this;
    }

    public static class Builder {
        private UUID identifier;
        private String externalIdentifier;
        private String firstName;
        private String lastName;
        private Gender gender;
        private LocalDate dateOfBirth;
        private PhoneNumber phoneNumber;
        private Email email;
        private String nationality;
        private AddressEntity residentialAddress;
        private String socialSecurityNumber;
        private String idDocumentPath;
        private String photoPath;

        public Builder identifier(UUID identifier) {
            Objects.requireNonNull(identifier, "identifier cannot be null");
            this.identifier = identifier;
            return this;
        }

        public Builder externalIdentifier(String externalIdentifier) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class,
                                             "externalIdentifier", externalIdentifier);
            this.externalIdentifier = externalIdentifier;
            return this;
        }

        public Builder firstName(String firstName) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class, "firstName", firstName);
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class, "lastName", lastName);
            this.lastName = lastName;
            return this;
        }

        public Builder gender(String gender) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class, "gender", gender);
            this.gender = new Gender(gender);
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            Objects.requireNonNull(dateOfBirth, "dateOfBirth cannot be null");
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = new PhoneNumber(phoneNumber);
            return this;
        }

        public Builder email(String email) {
            this.email = new Email(email);
            return this;
        }

        public Builder nationality(String nationality) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class, "nationality", nationality);
            this.nationality = nationality;
            return this;
        }

        public Builder residentialAddress(AddressData addressData) {
            Objects.requireNonNull(addressData, "residentialAddress cannot be null");
            this.residentialAddress =
                    AddressEntity.build().fromDataTransferObject(addressData);
            return this;
        }

        public Builder socialSecurityNumber(String socialSecurityNumber) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class, "socialSecurityNumber", socialSecurityNumber);
            this.socialSecurityNumber = socialSecurityNumber;
            return this;
        }

        public Builder idDocumentPath(String idDocumentPath) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class, "idDocumentPath", idDocumentPath);
            this.idDocumentPath = idDocumentPath;
            return this;
        }

        public Builder photoPath(String photoPath) {
            ExceptionUtil.throwIfNullOrEmpty(CustomerEntity.class, "photoPath", photoPath);
            this.photoPath = photoPath;
            return this;
        }

        public CustomerEntity build() {
            CustomerEntity e = new CustomerEntity();
            e.identifier = this.identifier;
            e.externalIdentifier = this.externalIdentifier;
            e.firstName = this.firstName;
            e.lastName = this.lastName;
            e.gender = this.gender;
            e.dateOfBirth = this.dateOfBirth;
            e.phoneNumber = this.phoneNumber;
            e.email = this.email;
            e.nationality = this.nationality;
            e.residentialAddress = this.residentialAddress;
            e.socialSecurityNumber = this.socialSecurityNumber;
            e.idDocumentPath = this.idDocumentPath;
            e.photoPath = this.photoPath;
            return e;
        }
    }
}
