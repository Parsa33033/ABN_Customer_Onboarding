package nl.abc.onboarding.customer.domain.ports.entities.exceptions;

public class EmailNotValidException extends DomainEntityException {

    public EmailNotValidException(String email) {
        super(String.format("The email '%s' is not valid.", email));
    }
}
