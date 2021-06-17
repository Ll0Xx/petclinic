package com.antont.petclinic.security.registration.validation;

import io.micrometer.core.instrument.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.isEmpty(value)) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.-]*$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } else {
            return false;
        }

    }
}
