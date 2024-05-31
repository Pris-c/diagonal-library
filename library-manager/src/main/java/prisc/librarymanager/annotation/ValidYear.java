package prisc.librarymanager.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prisc.librarymanager.validations.validator.YearValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for validating the year of publication.
 * This annotation is associated with the {@link YearValidator} class.
 */
@Constraint(validatedBy = YearValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYear {

    /**
     * Default error message when the year is invalid.
     *
     * @return Error message
     */
    String message() default "The year must be between 0 and the currentYear value";

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