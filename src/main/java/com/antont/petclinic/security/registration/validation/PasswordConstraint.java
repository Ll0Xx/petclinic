package com.antont.petclinic.security.registration.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    //ToDo move to messages.properties files
    String message() default "Password must have at least one letter, one number and one special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
