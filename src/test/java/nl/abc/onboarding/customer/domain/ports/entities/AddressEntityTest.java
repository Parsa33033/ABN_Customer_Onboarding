package nl.abc.onboarding.customer.domain.ports.entities;

import nl.abc.onboarding.customer.domain.ports.entities.exceptions.DomainEntityException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import nl.abc.onboarding.customer.domain.ports.dtos.AddressData;

public class AddressEntityTest {

    @Test
    void fromDataTransferObjectToDataTransferObject() {
        AddressData expected = new AddressData(
                "Baker Street 221B", "NW1 6XE", "London", "UK");

        AddressEntity entity = new AddressEntity.Builder()
                .address("Baker Street 221B")
                .zipCode("NW1 6XE")
                .city("London")
                .country("UK")
                .build();

        AddressData actual = entity.toDataTransferObject();
        assertEquals(expected, actual);
    }

    @Test
    void invalidNullZipCode() {
        DomainEntityException exception = assertThrows(
                DomainEntityException.class,
                () -> {
                    new AddressEntity.Builder()
                            .address("Baker Street 221B")
                            .zipCode(null)
                            .city("London")
                            .country("UK")
                            .build();
                });

        assertEquals(
                exception.getMessage(),
                "Domain Entity AddressEntity failed to build on attribute: " +
                        "zipCode. Invalid value provided. value should not be null or empty.");
    }

    @Test
    void invalidEmptyCity() {
        DomainEntityException exception = assertThrows(
                DomainEntityException.class, () -> {
                    new AddressEntity.Builder()
                            .address("Baker Street 221B")
                            .zipCode("NW1 6XE")
                            .city("")
                            .country("UK")
                            .build();
                });

        assertEquals(
                exception.getMessage(),
                "Domain Entity AddressEntity failed to build on attribute: " +
                        "city. Invalid value provided. value should not be null or empty.");
    }

    @Test
    void invalidNullCountry() {
        DomainEntityException exception = assertThrows(
                DomainEntityException.class, () -> {
                    new AddressEntity.Builder()
                            .address("Baker Street 221B")
                            .zipCode("NW1 6XE")
                            .city("London")
                            .country(null)
                            .build();
                });


        assertEquals(
                exception.getMessage(),
                "Domain Entity AddressEntity failed to build on attribute: " +
                        "country. Invalid value provided. value should not be null or empty.");
    }

    @Test
    void invalidEmptyAddress() {
        DomainEntityException exception = assertThrows(
                DomainEntityException.class, () -> {
                    new AddressEntity.Builder()
                            .address(null)
                            .zipCode("NW1 6XE")
                            .city("London")
                            .country("UK")
                            .build();
                });

        assertEquals(
                exception.getMessage(),
                "Domain Entity AddressEntity failed to build on attribute: " +
                        "address. Invalid value provided. value should not be null or empty.");
    }
}

