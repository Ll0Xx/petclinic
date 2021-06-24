package com.antont.petclinic.security.registration.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;
    private String errorMessage;

    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.errorMessage = constraintAnnotation.message();
    }

    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean isPasswordsMatch = false;

        try {
            String fieldValue = (String) new BeanWrapperImpl(value).getPropertyValue(field);
            String fieldMatchValue = (String) new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
            isPasswordsMatch = Objects.equals(fieldValue, fieldMatchValue);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
        if (!isPasswordsMatch) {
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addPropertyNode(field).addConstraintViolation();
        }

        return isPasswordsMatch;
    }
}