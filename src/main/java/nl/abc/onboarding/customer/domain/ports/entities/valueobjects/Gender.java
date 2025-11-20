package nl.abc.onboarding.customer.domain.ports.entities.valueobjects;

import nl.abc.onboarding.customer.framework.ValueObject;

public class Gender implements ValueObject<String> {

    private final GenderEnum gender;

    public Gender(String value) {
        this.validate(value);
        this.gender = GenderEnum.fromString(value);
    }

    @Override
    public void validate(String value) {
        GenderEnum.fromString(value);
    }

    @Override
    public String getValue() {
        return this.gender.value();
    }


    private enum GenderEnum {
        MALE("male"),
        FEMALE("female");

        private final String value;

        GenderEnum(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        public static GenderEnum fromString(String s) {
            if (s == null) return null;
            if (s.equalsIgnoreCase("male")) return MALE;
            if (s.equalsIgnoreCase("female")) return FEMALE;
            throw new IllegalArgumentException("Unknown gender: " + s);
        }

        @Override
        public String toString() {
            return value;
        }
    }
}

