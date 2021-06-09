package com.antont.petclinic;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SecurityLoginTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenValidRequestWithValidCredentials_shouldLoginSuccessfully() throws Exception {
        mockMvc
                .perform(formLogin("/perform_login").user("antont").password("aa12#aa"))
                .andExpect(status().isFound())
                .andExpect(authenticated().withUsername("antont"));
    }

    @Test
    public void givenValidRequestWithInvalidCredentials_shouldFailWith401() throws Exception {
        MvcResult result = mockMvc
                .perform(formLogin("/perform_login").user("random").password("random")).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Bad credentials"));
    }
}
