package nl.abc.onboarding.customer.domain.ports.entities.valueobjects;

import nl.abc.onboarding.customer.domain.ports.entities.exceptions.PhoneNumberNotValidException;
import nl.abc.onboarding.customer.framework.ValueObject;

import java.util.regex.Pattern;

public class PhoneNumber implements ValueObject<String> {

    private final String value;

    public PhoneNumber(String value) {
        validate(value);
        this.value = value;
    }

    @Override
    public void validate(String value) {
        // the regex pattern was created by AI
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        // Normalize common separators while keeping leading '+'
        String normalized = value.replaceAll("[\\s().-]", "");

        // E.164: +[country][number], 8-15 digits total (excluding '+')
        boolean isE164 = Pattern.compile("^\\+[1-9]\\d{7,14}$").matcher(normalized).matches();
        // NL local: 10 digits starting with 0 (e.g., 0612345678)
        boolean isNlLocal = Pattern.compile("^0\\d{9}$").matcher(normalized).matches();

        if (!(isE164 || isNlLocal)) {
            throw new PhoneNumberNotValidException(value);
        }
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
