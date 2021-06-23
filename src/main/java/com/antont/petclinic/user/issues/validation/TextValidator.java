package com.antont.petclinic.user.issues.validation;

import io.micrometer.core.instrument.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator implements ConstraintValidator<TextConstraint, String> {

    @Override
    public void initialize(TextConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.isEmpty(value)) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9 _,.?!\"'\\-(){}\\[\\]]*$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } else {
            return false;
        }
    }
}
