package game.pandemic.config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(final ConstraintViolationException exception) {
        final Map<String, String> errors = new HashMap<>();

        exception.getConstraintViolations().forEach(constraintViolation -> {
            final String parameter = constraintViolation.getPropertyPath().toString();
            final String message = constraintViolation.getMessage();
            errors.put(parameter, message);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}
