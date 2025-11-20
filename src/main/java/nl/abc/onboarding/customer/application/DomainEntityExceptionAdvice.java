package nl.abc.onboarding.customer.application;

import nl.abc.onboarding.customer.application.request.InternalError;
import nl.abc.onboarding.customer.application.request.InvalidRequest;
import nl.abc.onboarding.customer.domain.ports.entities.exceptions.DomainEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DomainEntityExceptionAdvice {

    @ExceptionHandler(DomainEntityException.class)
    public ResponseEntity<InvalidRequest> handleDomainEntityException(DomainEntityException ex) {
        InvalidRequest body = new InvalidRequest(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<InternalError> handleNullPointerException(NullPointerException ex) {
        InternalError body =
                new InternalError("internal error:" + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

