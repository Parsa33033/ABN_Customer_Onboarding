package nl.abc.onboarding.customer.framework;

public interface ValueObject<V> {

    void validate(V value);

    V getValue();
}
