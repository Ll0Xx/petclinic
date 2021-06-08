package com.antont.petclinic.security.registration.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {
    String message() default "Must be from 3 to 10 characters long";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
