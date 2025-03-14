package com.nnk.springboot.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        // Given
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("Password1!");
        user.setFullname("Test User");
        user.setRole("USER");

        // Then
        assertEquals(1, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("Password1!", user.getPassword());
        assertEquals("Test User", user.getFullname());
        assertEquals("USER", user.getRole());
    }


}