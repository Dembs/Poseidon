package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user1 = new User("user1", "Password1!", "User One", "USER");
        user1.setId(1);

        user2 = new User("user2", "Password2!", "User Two", "ADMIN");
        user2.setId(2);
    }

    @Test
    public void testHome() throws Exception {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/user/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("user/list"))
               .andExpect(model().attributeExists("users"))
               .andExpect(model().attribute("users", Arrays.asList(user1, user2)));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testAddUserForm() throws Exception {

        mockMvc.perform(get("/user/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("user/add"))
               .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/user/update/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("user/update"))
               .andExpect(model().attributeExists("user"));

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = new User("user1updated", "Password4!", "User One Updated", "ADMIN");
        updatedUser.setId(1);

        mockMvc.perform(post("/user/update/1")
                       .flashAttr("user", updatedUser))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/user/list"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).delete(user1);

        mockMvc.perform(get("/user/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/user/list"));

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(user1);
    }
}