package nl.abc.onboarding.customer.domain.services;

import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.incoming.CustomerService;
import nl.abc.onboarding.customer.domain.ports.outgoing.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class CustomerServiceImpTest {

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

    public static final String ADDRESS = "Main Street 123";
    public static final String ZIP = "Amsterdam";
    public static final String CITY = "1011AB";
    public static final String COUNTRY = "Netherlands";

    public static final String externalIdentifier = "external-12345";
    public static final java.util.UUID identifier = java.util.UUID.randomUUID();

    @Mock
    CustomerRepository customerRepository;

    ArgumentCaptor<CustomerData> argumentCaptorForWriteCustomerData;


    static CustomerData sampleCustomerData;
    static AddressData sampleAddressData;

    CustomerService customerService;

    @BeforeEach
    void init() {
        customerRepository =
                Mockito.mock(CustomerRepository.class);
        argumentCaptorForWriteCustomerData =
                ArgumentCaptor.forClass(CustomerData.class);
        sampleAddressData = new AddressData(
                ADDRESS,
                ZIP,
                CITY,
                COUNTRY);

        sampleCustomerData = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                sampleAddressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        customerService =
                new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void testOnboardSuccessful_ReturnedAnAlreadyFoundCustomer() {
        // GIVEN a customer already exists with the external identifier
        Mockito.when(customerRepository.readByExternalIdentifier(externalIdentifier))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(sampleCustomerData)));

        // WHEN service operation is called to onboard the customer with
        // CustomerData
        CompletableFuture<CustomerData> resultFuture =
                customerService.onboard(sampleCustomerData);

        // THEN returned result is the already existing customer data
        resultFuture.thenAccept(CustomerServiceImpTest::assertActualCustomerData).join();

    }

    @Test
    public void testOnboardSuccess_CustomerDoesNotAlreadyExistAndWillBeCreated() {
        // GIVEN Customer does not exist with the external identifier
        Mockito.when(customerRepository.readByExternalIdentifier(externalIdentifier))
                .thenReturn(CompletableFuture.completedFuture(Optional.empty()));

        Mockito.when(customerRepository.write(sampleCustomerData))
                .thenReturn(CompletableFuture.completedFuture(sampleCustomerData));


        // WHEN service operation is called to onboard the customer with CustomerData

        // also capture the argument passed to repository write as the actual
        // data being persisted that can be asserted later
        CompletableFuture<CustomerData> resultFuture =
                customerService.onboard(sampleCustomerData);
        Mockito.verify(customerRepository).write(argumentCaptorForWriteCustomerData.capture());
        CustomerData actualPersistedCustomer =
                argumentCaptorForWriteCustomerData.getValue();

        // THEN returned result is the newly created customer data
        resultFuture.thenAccept(CustomerServiceImpTest::assertActualCustomerData).join();

        // AND the actual persisted customer data is as expected
        assertActualCustomerData(actualPersistedCustomer);

    }

    public static void assertActualCustomerData(CustomerData actual) {
        // identifier UUID is not going to be tested here because it is created
        // inside the service during onboarding when customer does not exist
        CustomerData expected = sampleCustomerData;

        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.firstName(), actual.firstName()),
                () -> Assertions.assertEquals(expected.lastName(), actual.lastName()),
                () -> Assertions.assertEquals(expected.gender(), actual.gender()),
                () -> Assertions.assertEquals(expected.dateOfBirth(), actual.dateOfBirth()),
                () -> Assertions.assertEquals(expected.phoneNumber(), actual.phoneNumber()),
                () -> Assertions.assertEquals(expected.email(), actual.email()),
                () -> Assertions.assertEquals(expected.nationality(), actual.nationality()),
                () -> Assertions.assertEquals(expected.residentialAddress(), actual.residentialAddress()),
                () -> Assertions.assertEquals(expected.socialSecurityNumber(), actual.socialSecurityNumber()),
                () -> Assertions.assertEquals(expected.idDocumentPath(), actual.idDocumentPath()),
                () -> Assertions.assertEquals(expected.photoPath(), actual.photoPath())
        );

    }
}
