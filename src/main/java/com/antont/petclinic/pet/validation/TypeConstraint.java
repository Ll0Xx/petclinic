package com.antont.petclinic.pet.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TypeValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeConstraint {
    String message() default "{validation.invalidPetType}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
