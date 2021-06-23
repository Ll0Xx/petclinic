package com.antont.petclinic.user.issues.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {

    String message() default "{validation.date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}



