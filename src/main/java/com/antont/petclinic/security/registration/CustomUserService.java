package com.antont.petclinic.security.registration;

import com.antont.petclinic.security.registration.excepsion.UserAlreadyExistException;
import com.antont.petclinic.user.RoleRepository;
import com.antont.petclinic.user.User;
import com.antont.petclinic.user.UserRepository;
import com.antont.petclinic.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

//Not clear why are use using service name. It is best practice to create interface and service with same name?
@Service("userService")
public class CustomUserService implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(UserDto userDto) throws UserAlreadyExistException {

        if (checkIfUserExist(userDto.getUsername())) {
            throw new UserAlreadyExistException("User already exists for this email");
        }

        UserRole role = roleRepository.findByName("USER").orElseThrow();

        User user = initializeUserFromDto(userDto, role);
        userRepository.save(user);
    }

    //Not clear why do we need this method? We do not reuse this code
    private User initializeUserFromDto(UserDto userDto, UserRole role) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(Set.of(role));
        user.setActive(true);
        encodePassword(userDto, user);

        return user;
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.existsById(email);
    }

    private void encodePassword(UserDto userDto, User userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }
}