package nl.abc.onboarding.customer.infrastructure.repositories;

import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.infrastructure.repositories.entities.Address;
import nl.abc.onboarding.customer.infrastructure.repositories.entities.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String GENDER = "male";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1985, 5, 20);
    public static final String PHONE_NUMBER = "+31 6 12345678";
    public static final String EMAIL = "john.doe@example.com";
    public static final String NATIONALITY = "Dutch";
    public static final String SOCIAL_SECURITY_NUMBER = "123-45-6789";
    public static final String ID_DOCUMENT_PATH = "/tmp/id_document.pdf";
    public static final String PHOTO_PATH = "/tmp/photo.jpg";
    public static final UUID identifier = UUID.randomUUID();
    public static final String externalIdentifier = "ext-1";

    public static final AddressData addressData = new AddressData(
            "Main Street 1", "1234 AB", "Amsterdam",
            "Netherlands");

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void deleteBefore() {
        repository.deleteAll();
    }

    @AfterEach
    void deleteAfter() {
        repository.deleteAll();
    }

    @Test
    void saveAndFindByExternalIdentifier() {

        CustomerData expectedCustomerToPersist = new CustomerData(
                identifier, externalIdentifier, FIRST_NAME, LAST_NAME, GENDER,
                DATE_OF_BIRTH, PHONE_NUMBER, EMAIL, NATIONALITY, addressData,
                SOCIAL_SECURITY_NUMBER, ID_DOCUMENT_PATH, PHOTO_PATH);

        Customer customer = Customer.fromDataTransferObject(expectedCustomerToPersist);

        Customer saved = repository.save(customer);
        assertNotNull(saved);
        assertEquals(identifier, saved.getIdentifier());

        Optional<Customer> found = repository.findByExternalIdentifier("ext-1");
        assertTrue(found.isPresent(), "Customer should be found by external identifier");
        Customer f = found.get();
        assertEquals(saved.getIdentifier(), f.getIdentifier());
        assertNotNull(f.getResidentialAddress(), "Address should be present");
        assertEquals("Main Street 1", f.getResidentialAddress().getAddress());
    }
}

