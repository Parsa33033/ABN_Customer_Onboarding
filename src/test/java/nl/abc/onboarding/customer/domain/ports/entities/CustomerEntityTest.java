package nl.abc.onboarding.customer.domain.ports.entities;

import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;
import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;
import nl.abc.onboarding.customer.domain.ports.exceptions.DomainEntityException;
import nl.abc.onboarding.customer.domain.ports.exceptions.EmailNotValidException;
import nl.abc.onboarding.customer.domain.ports.exceptions.PhoneNumberNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerEntityTest {
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
    UUID identifier;
    String externalIdentifier;
    AddressData addressData;

    @BeforeEach
    public void init() {
        identifier = UUID.randomUUID();
        externalIdentifier = "EXT-" + UUID.randomUUID();
        addressData = new AddressData("Main Street 1", "1234 AB",
                                      "Amsterdam", "Netherlands");
    }

    @Test
    void fromDataTransferObjectToDataTransferObject() {

        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );


        CustomerEntity customer =
                CustomerEntity.build().fromDataTransferObject(expected);

        CustomerData actual = customer.toDataTransferObject();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testInvalidEmailThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                "invalid email",
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        EmailNotValidException exception =
                Assertions.assertThrows(EmailNotValidException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("The email 'invalid email' is not valid.", exception.getMessage());

    }

    @Test
    void testInvalidPhoneNumberThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                "invalid phone number",
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        PhoneNumberNotValidException exception =
                Assertions.assertThrows(PhoneNumberNotValidException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("The phoneNumber 'invalid phone number' is not valid.", exception.getMessage());

    }

    @Test
    void testInvalidGenderThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                "invalid",
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        IllegalArgumentException exception =
                Assertions.assertThrows(IllegalArgumentException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Unknown gender: invalid", exception.getMessage());

    }

    @Test
    void testInvalidAddressThrowsException() {

        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                null,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("residentialAddress cannot be null", exception.getMessage());

    }

    @Test
    void testInvalidIdentifierThrowsException() {

        CustomerData expected = new CustomerData(
                null,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("identifier cannot be null",
                                exception.getMessage());

    }

    // ...additional tests for other fields can be added similarly

    @Test
    void testInvalidExternalIdentifierThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                null,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: externalIdentifier. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }

    @Test
    void testInvalidFirstNameThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                null,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: firstName. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }

    @Test
    void testInvalidLastNameThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                null,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: lastName. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }

    @Test
    void testNullGenderThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                null,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: gender. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }

    @Test
    void testInvalidDateOfBirthThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                null,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        NullPointerException exception =
                Assertions.assertThrows(NullPointerException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("dateOfBirth cannot be null", exception.getMessage());
    }

    @Test
    void testInvalidNationalityThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                null,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: nationality. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }

    @Test
    void testInvalidSocialSecurityNumberThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                null,
                ID_DOCUMENT_PATH,
                PHOTO_PATH
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: socialSecurityNumber. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }

    @Test
    void testInvalidIdDocumentPathThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                null,
                PHOTO_PATH
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: idDocumentPath. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }

    @Test
    void testInvalidPhotoPathThrowsException() {
        CustomerData expected = new CustomerData(
                identifier,
                externalIdentifier,
                FIRST_NAME,
                LAST_NAME,
                GENDER,
                DATE_OF_BIRTH,
                PHONE_NUMBER,
                EMAIL,
                NATIONALITY,
                addressData,
                SOCIAL_SECURITY_NUMBER,
                ID_DOCUMENT_PATH,
                null
        );
        DomainEntityException exception =
                Assertions.assertThrows(DomainEntityException.class,
                                        () -> CustomerEntity.build().fromDataTransferObject(expected));

        Assertions.assertEquals("Domain Entity CustomerEntity failed to build on attribute: photoPath. Invalid value provided. value should not be null or empty.",
                                exception.getMessage());
    }
}
