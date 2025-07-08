package game.pandemic.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Log4j2
public class ValidationService {
    private final Validator validator;

    public <T> void validate(final T object,
                             final Consumer<T> validCallback) {
        validate(object, validCallback, createDefaultInvalidCallbackWithLoggerForObject(object));
    }

    private <T> Consumer<Set<ConstraintViolation<T>>> createDefaultInvalidCallbackWithLoggerForObject(final T object) {
        final String objectType;
        if (object != null) {
            objectType = " of type " + object.getClass().getName();
        } else {
            objectType = "";
        }
        return violations -> log.warn(
                "Given object" + objectType + " is invalid. There are " + violations.size() + " constraint violations."
        );
    }

    public <T> void validate(final T object,
                             final Consumer<T> validCallback,
                             final Consumer<Set<ConstraintViolation<T>>> invalidCallback) {
        final Set<ConstraintViolation<T>> violations = this.validator.validate(object);

        if (violations.isEmpty()) {
            validCallback.accept(object);
        } else {
            invalidCallback.accept(violations);
        }
    }
}
