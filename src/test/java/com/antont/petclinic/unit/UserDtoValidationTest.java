package com.antont.petclinic.unit;

import com.antont.petclinic.security.registration.UserDto;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDtoValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testUserDtoModelWithValidData_shouldSuccessValidation() {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setFirstName("Jack");
        userDto.setLastName("Jackson");
        userDto.setPassword("aa12#aa");
        userDto.setMatchingPassword("aa12#aa");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testUserDtoModelWithInvalidUsername_shouldFailValidation() {
        UserDto userDto = new UserDto();
        userDto.setUsername("username!@#");
        userDto.setFirstName("Jack");
        userDto.setLastName("Jackson");
        userDto.setPassword("aa12#aa");
        userDto.setMatchingPassword("aa12#aa");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(violations.size(), 1);
        assertTrue(violations.stream().allMatch(it -> it.getMessageTemplate().equals("{validation.invalidName}")));

        userDto.setUsername(null);
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 1);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidName}")));

        userDto.setUsername("");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")));
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidName}")));

        userDto.setUsername("12");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 1);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")));
    }

    @Test
    public void testUserDtoModelWithInvalidName_shouldFailValidation() {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setFirstName("J@ck");
        userDto.setLastName("J@ckson");
        userDto.setPassword("aa12#aa");
        userDto.setMatchingPassword("aa12#aa");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().allMatch(it -> it.getMessageTemplate().equals("{validation.invalidName}")));

        userDto.setFirstName(null);
        userDto.setLastName(null);
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidName}")));


        userDto.setFirstName("");
        userDto.setLastName("");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 4);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")));
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidName}")));

        userDto.setFirstName("12");
        userDto.setLastName("45");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")));

    }

    @Test
    public void testUserDtoModelWithMatchingInvalidPasswords_shouldFailValidation() {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setFirstName("Jack");
        userDto.setLastName("Jackson");
        userDto.setPassword("password");
        userDto.setMatchingPassword("password");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().allMatch(it -> it.getMessageTemplate().equals("{validation.invalidPassword}")));

        userDto.setPassword(null);
        userDto.setMatchingPassword(null);
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidPassword}")));

        userDto.setPassword("");
        userDto.setMatchingPassword("");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 4);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidPassword}")));
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")));

        userDto.setPassword("p@a1");
        userDto.setMatchingPassword("p@a1");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{javax.validation.constraints.Size.message}")));
    }

    @Test
    public void testUserDtoModelWithNotMatchingPasswords_shouldFailValidation() {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setFirstName("Jack");
        userDto.setLastName("Jackson");
        userDto.setPassword("aa12#aa");
        userDto.setMatchingPassword("aa23$aa");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(violations.size(), 2);
        assertTrue(violations.stream().allMatch(it -> it.getMessageTemplate().equals("{validation.passwordsNotMatch}")));

        userDto.setPassword(null);
        userDto.setMatchingPassword("aa12#aa");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 3);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.passwordsNotMatch}")));
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidPassword}")));

        userDto.setPassword("password");
        userDto.setMatchingPassword("anotherpass");
        violations = validator.validate(userDto);
        assertEquals(violations.size(), 4);
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.passwordsNotMatch}")));
        assertTrue(violations.stream().anyMatch(it -> it.getMessageTemplate().equals("{validation.invalidPassword}")));
    }
}
