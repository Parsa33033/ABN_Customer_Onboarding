package nl.abc.onboarding.customer.domain.ports.exceptions;

public class PhoneNumberNotValidException extends RuntimeException {

    public PhoneNumberNotValidException(String phoneNumber) {
        super(String.format("The phoneNumber '%s' is not valid.", phoneNumber));
    }
}
