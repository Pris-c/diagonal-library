package prisc.diagonallibrary.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import prisc.diagonallibrary.validator.YearValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYear {

    String message() default "Invalid year";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}