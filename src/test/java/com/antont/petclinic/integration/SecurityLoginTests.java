package com.antont.petclinic.integration;

import com.antont.petclinic.security.registration.CustomUserService;
import com.antont.petclinic.security.registration.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SecurityLoginTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomUserService userService;

    @Test
    @Transactional
    public void givenValidRequestWithValidCredentials_shouldLoginSuccessfully() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("username");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPassword("password");
        userService.register(user);

        mockMvc
                .perform(formLogin("/perform_login").user(user.getUsername()).password(user.getPassword()))
                .andExpect(status().isFound())
                .andExpect(authenticated().withUsername(user.getUsername()));
    }

    @Test
    public void givenValidRequestWithInvalidCredentials_shouldFailWithRedirection() throws Exception {
        mockMvc
                .perform(formLogin("/perform_login").user("random").password("random"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/login?error=true"));
    }

    @Test
    @WithMockUser()
    public void givenValidRequestToOpenUserPageWithValidCredentials_shouldOpenSuccessfully() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"ANONYMOUS"})
    public void givenValidRequestToOpenUserPageWithInvalidCredentials_shouldFailWith403() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().is4xxClientError());
    }
}
