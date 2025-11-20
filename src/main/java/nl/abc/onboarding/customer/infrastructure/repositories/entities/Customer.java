package nl.abc.onboarding.customer.infrastructure.repositories.entities;

import jakarta.persistence.*;
import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(
        name = "customer_external_identifier",
        columnNames = "external_identifier"))
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
    @JoinColumn(name = "residential_address_id", referencedColumnName = "id",
                unique = true)
    private Address residentialAddress;

    @Column(name = "social_security_number")
    private String socialSecurityNumber;

    @Column(name = "id_document_path")
    private String idDocumentPath;

    @Column(name = "photo_path")
    private String photoPath;

    public Customer() {
    }

    public Customer(UUID identifier, String externalIdentifier,
                    String firstName, String lastName, String gender,
                    LocalDate dateOfBirth, String phoneNumber, String email,
                    String nationality, Address residentialAddress,
                    String socialSecurityNumber, String idDocumentPath,
                    String photoPath) {
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

    public static Customer fromDataTransferObject(CustomerData data) {
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

    public static CustomerData toDataTransferObject(Customer customer) {
        if (customer == null) return null;

        AddressData residentialAddress = null;
        Address addr = customer.residentialAddress;
        if (addr != null) {
            residentialAddress = new AddressData(
                    addr.getAddress(),
                    addr.getZipCode(),
                    addr.getCity(),
                    addr.getCountry()
            );
        }

        return new CustomerData(
                customer.getIdentifier(),
                customer.getExternalIdentifier(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getGender(),
                customer.getDateOfBirth(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getNationality(),
                residentialAddress,
                customer.getSocialSecurityNumber(),
                customer.getIdDocumentPath(),
                customer.getPhotoPath()
        );
    }

    // Getters and Setters

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public String getExternalIdentifier() {
        return externalIdentifier;
    }

    public void setExternalIdentifier(String externalIdentifier) {
        this.externalIdentifier = externalIdentifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Address getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(Address residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getIdDocumentPath() {
        return idDocumentPath;
    }

    public void setIdDocumentPath(String idDocumentPath) {
        this.idDocumentPath = idDocumentPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

}
