package prisc.diagonallibrary.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import prisc.diagonallibrary.annotation.ValidYear;

import java.time.Year;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        // Validation: The year must be between 0 and the current year
        int currentYear = Year.now().getValue();
        return year != null && year >= 0 && year <= currentYear;
    }
}
