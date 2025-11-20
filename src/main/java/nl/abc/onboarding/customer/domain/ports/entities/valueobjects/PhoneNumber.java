package nl.abc.onboarding.customer.domain.ports.entities.valueobjects;

import nl.abc.onboarding.customer.domain.ports.entities.exceptions.PhoneNumberNotValidException;
import nl.abc.onboarding.framework.ValueObject;

import java.util.regex.Pattern;

public class PhoneNumber implements ValueObject<String> {

    private final String value;

    public PhoneNumber(String value) {
        validate(value);
        this.value = value;
    }

    @Override
    public void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        // Accepts optional country code and common separators: spaces, dashes, dots, and parentheses
        Pattern phonePattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\(?\\d{1,4}\\)?[- .]?\\d{1,4}[- .]?\\d{1,9}$");
        if (!phonePattern.matcher(value).matches()) {
            throw new PhoneNumberNotValidException(value);
        }
    }

    @Override
    public String getValue() {
        return this.value;
    }
}

