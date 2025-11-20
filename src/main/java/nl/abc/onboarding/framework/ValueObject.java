package nl.abc.onboarding.framework;

public interface ValueObject<V> {

    void validate(V value);

    V getValue();
}
