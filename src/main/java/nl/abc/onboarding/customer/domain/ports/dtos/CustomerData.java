package nl.abc.onboarding.customer.domain.ports.dtos;

import nl.abc.onboarding.customer.framework.DataTransferObject;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerData(
        UUID identifier,
        String externalIdentifier,
        String firstName,
        String lastName,
        String gender,
        LocalDate dateOfBirth,
        String phoneNumber,
        String email,
        String nationality,
        String residentialAddress,
        String socialSecurityNumber,
        String idDocumentPath,
        String photoPath,
        OnboardingStatus onboardingStatus,
        String accountNumber
) implements DataTransferObject {
}
