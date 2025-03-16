package com.nnk.springboot.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void loginSuccess() throws Exception {
        mockMvc.perform(formLogin("/perform_login").user("admin").password("Test123."))
               .andExpect(authenticated())
               .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginFailure() throws Exception {
        mockMvc.perform(formLogin("/perform_login").user("admin").password("mauvais_mot_de_passe"))
               .andExpect(unauthenticated());
    }

    // Redirection basée sur les rôles

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void accessUserListWithAdminRole() throws Exception {
        mockMvc.perform(get("/user/list").with(csrf()))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    public void accessBidListWithUserRole() throws Exception {
        mockMvc.perform(get("/bidList/list").with(csrf()))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"USER"})
    public void accessUserListWithUserRoleShouldBeDenied() throws Exception {
        mockMvc.perform(get("/user/list").with(csrf()))
               .andExpect(status().isForbidden());
    }

}