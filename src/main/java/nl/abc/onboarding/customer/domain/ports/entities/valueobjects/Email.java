package nl.abc.onboarding.customer.domain.ports.entities.valueobjects;

import nl.abc.onboarding.customer.domain.ports.entities.exceptions.EmailNotValidException;
import nl.abc.onboarding.framework.ValueObject;

import java.util.regex.Pattern;

public class Email implements ValueObject<String> {

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    @Override
    public void validate(String value) {
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        if (!emailPattern.matcher(value).matches()) {
            throw new EmailNotValidException(value);
        }
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
