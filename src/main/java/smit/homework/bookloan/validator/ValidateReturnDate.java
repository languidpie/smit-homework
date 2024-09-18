package smit.homework.bookloan.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation to validate that a {@link java.time.LocalDateTime} return date is within a valid range for book returns.
 * The return date must be between the current date and time and four weeks from the current date and time.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 18.09.2024
 */
@Documented
@Constraint(validatedBy = ReturnDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateReturnDate {
    String message() default "Return date must be within 4 weeks from today";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
