package com.antont.petclinic.security.registration.validation;

import org.springframework.util.StringUtils;

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
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.-]*$");
        Matcher matcher = pattern.matcher(value);
        boolean isContentCorrect = matcher.matches();

        if (!isContentCorrect) {
            context.buildConstraintViolationWithTemplate("Only alphanumeric characters are allowed").addConstraintViolation();
        }

        return isContentCorrect;
    }
}
