package nl.abc.onboarding.customer.domain.ports.exceptions;

public class PhoneNumberNotValidException extends DomainEntityException {

    public PhoneNumberNotValidException(String phoneNumber) {
        super(String.format("The phoneNumber '%s' is not valid.", phoneNumber));
    }
}
