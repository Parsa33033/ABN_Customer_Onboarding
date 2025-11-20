package nl.abc.onboarding.customer.domain.ports.entities.exceptions;

public class ExceptionUtil {

    public static void throwIfNullOrEmpty(Class<?> entityClass,
                                          String attribute,
                                          String value) {
        if (value == null || value.isBlank()) {
            throw new DomainEntityException(entityClass, attribute);
        }
    }
}
