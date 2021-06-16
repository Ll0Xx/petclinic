package com.antont.petclinic.security.registration;

import com.antont.petclinic.security.registration.excepsion.UserAlreadyExistException;
import com.antont.petclinic.user.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
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
            throw new UserAlreadyExistException("{exceptions.userAlreadyExist}");
        }

        UserRole role = roleRepository.findByName(UserRoleName.USER.name()).orElseThrow();

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRoles(Set.of(role));
        user.setActive(true);
        encodePassword(userDto, user);

        userRepository.save(user);
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.existsById(email);
    }

    private void encodePassword(UserDto userDto, User userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }
}