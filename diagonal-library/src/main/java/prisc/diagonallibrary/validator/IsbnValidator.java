package prisc.diagonallibrary.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.ISBNValidator;
import prisc.diagonallibrary.annotation.ValidIsbn;

/**
 * Validator class for validating the isbn {@link ValidIsbn}.
 *.
 */
public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    ISBNValidator validator = ISBNValidator.getInstance();

    /**
     * Validates the isbn value.
     *
     * @param isbn    The volume identifier.
     * @param context The validation context.
     * @return True if the isbn is valid; false otherwise.
     */
    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
    // TODO: Custom error messages
        return validator.isValid(isbn);
    }

}
