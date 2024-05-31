package prisc.diagonallibrary.validations.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import prisc.diagonallibrary.annotation.ValidYear;

import java.time.Year;

/**
 * Validator class for validating the year attribute annotated with {@link ValidYear}.
 * It ensures that the year is a non-negative value and does not exceed the current year.
 */
public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    /**
     * Validates the year value based on the specified constraints.
     *
     * @param year    The year value to be validated.
     * @param context The validation context.
     * @return True if the year is valid; false otherwise.
     */
    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        // Validation: The year must be between 0 and the current year
        int currentYear = Year.now().getValue();
        return year != null && year >= 0 && year <= currentYear;
    }
}
