package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = new User("user1", "Password1!", "User One", "USER");
        user1.setId(1);

        user2 = new User("user2", "Password2!", "User Two", "ADMIN");
        user2.setId(2);
    }

    @Test
    public void testFindAll() {

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        Optional<User> result = userService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUsername());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {

        User newUser = new User("newuser", "Password3!", "New User", "USER");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(3);
            return savedUser;
        });

        User result = userService.save(newUser);

        assertEquals(3, result.getId());
        assertEquals("newuser", result.getUsername());

        assertNotEquals("Password3!", result.getPassword());
        assertTrue(result.getPassword().startsWith("$2a$"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdate() {

        User updatedUser = new User("user1updated", "Password4!", "User One Updated", "ADMIN");
        updatedUser.setId(1);

        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(updatedUser);

        assertEquals("user1updated", result.getUsername());
        assertEquals("User One Updated", result.getFullname());
        assertEquals("ADMIN", result.getRole());
        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    public void testDelete() {

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).delete(user1);

        userService.delete(1);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(user1);
    }

}