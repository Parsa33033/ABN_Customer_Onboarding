package nl.abc.onboarding.customer.application.response;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

import java.util.UUID;

public record CustomerResponse(UUID identifier, String externalIdentifier) {

    public static CustomerResponse from(CustomerData data) {
        if (data == null) return null;
        return new CustomerResponse(data.identifier(), data.externalIdentifier());
    }
}
