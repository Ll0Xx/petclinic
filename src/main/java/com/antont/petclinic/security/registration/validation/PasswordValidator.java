package com.antont.petclinic.security.registration.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.hasText(value)) {
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[A-Za-z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{3,}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);

            return matcher.matches();
        } else {
            return false;
        }
    }
}
