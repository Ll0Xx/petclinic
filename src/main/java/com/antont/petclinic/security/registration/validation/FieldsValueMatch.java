package com.antont.petclinic.security.registration.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {

    String message() default "Passwords do not match!";

    String field();

    String fieldMatch();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List
    {
        FieldsValueMatch[] value();
    }
}