package com.antont.petclinic.security.registration;

import com.antont.petclinic.security.registration.excepsion.UserAlreadyExistException;
import com.antont.petclinic.user.RoleRepository;
import com.antont.petclinic.user.User;
import com.antont.petclinic.user.UserRepository;
import com.antont.petclinic.user.UserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = new User();
        UserRole role = roleRepository.findByName("USER").orElseThrow();

        BeanUtils.copyProperties(userDto, user);
        user.setRoles(Set.of(role));
        user.setActive(true);
        encodePassword(user);
        userRepository.save(user);
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findUserByUsername(email).isPresent();
    }

    private void encodePassword(User userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    }
}