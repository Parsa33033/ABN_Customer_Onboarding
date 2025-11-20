package nl.abc.onboarding.customer.domain.ports.exceptions;

public class DomainEntityException extends RuntimeException {

    public DomainEntityException(String message) {
        super(message);
    }

    public DomainEntityException(Class<?> entityClass, String attributeName) {
        super(String.format("Domain Entity %s failed to build on attribute: " +
                                    "%s. Invalid value provided. value should" +
                                    " not be null or empty.",
                entityClass.getSimpleName(), attributeName));
    }
}
