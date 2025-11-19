package nl.abc.onboarding.customer.domain.ports.entities;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.dtos.OnboardingStatus;
import nl.abc.onboarding.customer.framework.DomainEntity;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerEntity implements DomainEntity<CustomerData> {

    private UUID identifier;
    private String externalIdentifier;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private String nationality;
    private String residentialAddress;
    private String socialSecurityNumber;
    private String idDocumentPath;
    private String photoPath;
    private OnboardingStatus onboardingStatus;
    private String accountNumber;

    private CustomerEntity() {}

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public CustomerData toDataTransferObject() {
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
                this.photoPath,
                this.onboardingStatus,
                this.accountNumber
        );
    }

    @Override
    public DomainEntity<CustomerData> fromDataTransferObject(CustomerData dataTransferObject) {
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
                .onboardingStatus(dataTransferObject.onboardingStatus())
                .accountNumber(dataTransferObject.accountNumber())
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
        this.onboardingStatus = built.onboardingStatus;
        this.accountNumber = built.accountNumber;

        return this;
    }

    public static class Builder {
        private UUID identifier;
        private String externalIdentifier;
        private String firstName;
        private String lastName;
        private String gender;
        private LocalDate dateOfBirth;
        private String phoneNumber;
        private String email;
        private String nationality;
        private String residentialAddress;
        private String socialSecurityNumber;
        private String idDocumentPath;
        private String photoPath;
        private OnboardingStatus onboardingStatus;
        private String accountNumber;

        public Builder identifier(UUID identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder externalIdentifier(String externalIdentifier) {
            this.externalIdentifier = externalIdentifier;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Builder residentialAddress(String residentialAddress) {
            this.residentialAddress = residentialAddress;
            return this;
        }

        public Builder socialSecurityNumber(String socialSecurityNumber) {
            this.socialSecurityNumber = socialSecurityNumber;
            return this;
        }

        public Builder idDocumentPath(String idDocumentPath) {
            this.idDocumentPath = idDocumentPath;
            return this;
        }

        public Builder photoPath(String photoPath) {
            this.photoPath = photoPath;
            return this;
        }

        public Builder onboardingStatus(OnboardingStatus onboardingStatus) {
            this.onboardingStatus = onboardingStatus;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
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
            e.onboardingStatus = this.onboardingStatus;
            e.accountNumber = this.accountNumber;
            return e;
        }
    }
}
