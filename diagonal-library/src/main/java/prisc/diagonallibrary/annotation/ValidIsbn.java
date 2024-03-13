package prisc.diagonallibrary.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prisc.diagonallibrary.validator.IsbnValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsbnValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIsbn {


    /**
     * Default error message when the isbn is invalid.
     *
     * @return Error message
     */
    String message() default "This is not a valid isbn value";

    /**
     * Groups targeted for validation.
     *
     * @return Array of groups.
     */
    Class<?>[] groups() default {};

    /**
     * Payload type to associate with the constraint.
     *
     * @return Array of payload classes.
     */
    Class<? extends Payload>[] payload() default {};


}
