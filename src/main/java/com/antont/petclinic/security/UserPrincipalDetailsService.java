package com.antont.petclinic.security;

import com.antont.petclinic.user.User;
import com.antont.petclinic.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Optional<User> appUser = userRepository.findUserByUsername(username);
        if (appUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new AuthenticatedUser(appUser.orElseThrow());
    }
}