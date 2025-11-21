package nl.abc.onboarding.customer.application.response;

import nl.abc.onboarding.customer.domain.ports.dtos.CustomerData;

import java.util.UUID;

public record CustomerResponse(UUID identifier, String externalIdentifier,
                               String loginInstructions) {

    public static CustomerResponse from(CustomerData data) {
        if (data == null) return null;
        return new CustomerResponse(data.identifier(),
                                    data.externalIdentifier(),
                                    generateInstructions(data.identifier()));
    }

    public static String generateInstructions(UUID identifier) {
        return "Use your external identifier as username and password: " +
                generateTemporaryPasswordUsingIdentifier(identifier);
    }

    public static String generateTemporaryPasswordUsingIdentifier(UUID identifier) {
        String hex = identifier.toString().replace("-", "");
        return hex.length() >= 8 ? hex.substring(0, 8).toUpperCase() : hex.toUpperCase();
    }
}
