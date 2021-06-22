package com.antont.petclinic.security.registration;

import com.antont.petclinic.security.registration.validation.FieldsValueMatch;
import com.antont.petclinic.security.registration.validation.NameConstraint;
import com.antont.petclinic.security.registration.validation.PasswordConstraint;

import javax.validation.constraints.Size;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "matchingPassword"
        )
})
public class UserDto {

    @NameConstraint()
    @Size(min = 3, max = 20)
    private String username;

    @NameConstraint()
    @Size(min = 3, max = 20)
    private String firstName;

    @NameConstraint
    @Size(min = 3, max = 20)
    private String lastName;

    @PasswordConstraint
    @Size(min = 5, max = 20)
    private String password;

    @PasswordConstraint
    @Size(min = 5, max = 20)
    private String matchingPassword;

    private boolean isDoctor = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public boolean getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(boolean doctor) {
        isDoctor = doctor;
    }
}