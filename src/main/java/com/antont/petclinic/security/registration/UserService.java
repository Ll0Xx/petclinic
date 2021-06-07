package com.antont.petclinic.security.registration;

import com.antont.petclinic.security.registration.excepsion.UserAlreadyExistException;

public interface UserService {
    void register(final UserDto user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
}