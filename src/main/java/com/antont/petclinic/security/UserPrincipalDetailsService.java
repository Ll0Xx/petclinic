package com.antont.petclinic.security;

import com.antont.petclinic.exception.NotFoundExceptions;
import com.antont.petclinic.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(AuthenticatedUser::new)
                .orElseThrow(() -> new com.antont.petclinic.exception.NotFoundExceptions("NotFound.userDetailsService.loadUser",
                        "User not found with username ${username}, in UserPrincipalDetailsService.class",
                        new Object[]{username}));
    }
}