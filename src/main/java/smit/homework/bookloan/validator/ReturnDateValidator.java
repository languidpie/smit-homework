package smit.homework.bookloan.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

/**
 * Validator for {@link ValidateReturnDate} annotation to ensure that a given
 * {@link LocalDateTime} return date is within 4 weeks from the current date and time, and not before.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 18.09.2024
 */
public class ReturnDateValidator implements ConstraintValidator<ValidateReturnDate, LocalDateTime> {

    @Override
    public void initialize(ValidateReturnDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime returnDate, ConstraintValidatorContext constraintValidatorContext) {
        if (returnDate == null) {
            return true;
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fourWeeksFromNow = now.plusWeeks(4);

        return !returnDate.isBefore(now) && !returnDate.isAfter(fourWeeksFromNow);
    }
}
