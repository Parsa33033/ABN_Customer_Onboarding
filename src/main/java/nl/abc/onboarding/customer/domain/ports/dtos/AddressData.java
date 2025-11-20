package nl.abc.onboarding.customer.domain.ports.dtos;

import nl.abc.onboarding.customer.framework.DataTransferObject;

public record AddressData(String address, String zipCode, String city,
                          String Country) implements DataTransferObject {
}
