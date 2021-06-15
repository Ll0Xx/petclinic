package com.antont.petclinic.security.registration;

import com.antont.petclinic.security.registration.excepsion.UserAlreadyExistException;
import com.antont.petclinic.user.RoleRepository;
import com.antont.petclinic.user.User;
import com.antont.petclinic.user.UserRepository;
import com.antont.petclinic.user.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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

        Optional<UserRole> role = roleRepository.findByName(userDto.getIsDoctor() ? "DOCTOR" : "USER");

        if (role.isPresent()) {
            User user = initializeUserFromDto(userDto, role.get());
            userRepository.save(user);
        }



    }

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