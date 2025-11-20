package nl.abc.onboarding.customer.application.request;

public class InternalError {
    private final String message;

    public InternalError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

