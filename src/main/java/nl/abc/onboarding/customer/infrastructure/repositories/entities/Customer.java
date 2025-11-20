package nl.abc.onboarding.customer.infrastructure.repositories.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;

@Getter
@Setter
@Entity
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(name = "uk_customer_external_identifier", columnNames = "external_identifier"))
public class Customer {

    @Id
    @Column(name = "identifier", nullable = false, updatable = false)
    private UUID identifier;

    @Column(name = "external_identifier", nullable = false, unique = true)
    private String externalIdentifier;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "nationality")
    private String nationality;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "residential_address_id", referencedColumnName = "id", unique = true)
    private Address residentialAddress;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "id_document_path")
    private String idDocumentPath;

    @Column(name = "photo_path")
    private String photoPath;

    public Customer() {
    }

    public Customer(UUID identifier, String externalIdentifier, String firstName, String lastName, String gender, LocalDate dateOfBirth, String phoneNumber, String email, String nationality, Address residentialAddress, String socialSecurityNumber, String idDocumentPath, String photoPath) {
        this.identifier = identifier;
        this.externalIdentifier = externalIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationality = nationality;
        this.residentialAddress = residentialAddress;
        this.socialSecurityNumber = socialSecurityNumber;
        this.idDocumentPath = idDocumentPath;
        this.photoPath = photoPath;
    }

    // Mapper: construct entity from domain DTO
    public static Customer from(CustomerData data) {
        if (data == null) return null;

        Address residential = null;
        AddressData adr = data.residentialAddress();
        if (adr != null) {
            residential = new Address();
            residential.setAddress(adr.address());
            residential.setZipCode(adr.zipCode());
            residential.setCity(adr.city());
            residential.setCountry(adr.Country());
        }

        Customer customer = new Customer(
                data.identifier(),
                data.externalIdentifier(),
                data.firstName(),
                data.lastName(),
                data.gender(),
                data.dateOfBirth(),
                data.phoneNumber(),
                data.email(),
                data.nationality(),
                residential,
                data.socialSecurityNumber(),
                data.idDocumentPath(),
                data.photoPath()
        );

        if (residential != null) {
            residential.setCustomer(customer);
        }

        return customer;
    }
}
