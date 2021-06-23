package com.antont.petclinic.user.issues.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TextValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TextConstraint {
    String message() default "{validation.text}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
