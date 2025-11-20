package nl.abc.onboarding.customer.domain.ports.entities.exceptions;

public class ExceptionUtil {

    public static void throwIfNull(Class<?> entityClass,
                                   String attribute,
                                   Object value) {
        if (value == null) {
            throw new DomainEntityException(entityClass, attribute);
        }
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                throw new DomainEntityException(entityClass, attribute);
            }
        }
    }
}
