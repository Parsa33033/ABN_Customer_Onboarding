package nl.abc.onboarding.customer.application.request;

public class InvalidRequest {
    private final String message;

    public InvalidRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

