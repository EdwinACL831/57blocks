package com.example.hw57blocks.services;

import com.example.hw57blocks.entities.UserEntity;
import com.example.hw57blocks.models.User;
import com.example.hw57blocks.repositories.UserRepository;
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static final String DUMMY_EMAIL = "dummy.email@company.com";
    private static final String DUMMY_PASSWORD = "Dummy|?word#";

    private User user;
    private UserEntity userEntity;
    private UserRepository userRepository;
    private UserService userService;

    public UserServiceTest() {
    }

    @BeforeEach
    public void setup() {
        user = mock(User.class);
        userRepository = mock(UserRepository.class);
        userEntity = mock(UserEntity.class);
        userService = new UserService(userRepository);

        when(user.getEmail()).thenReturn(DUMMY_EMAIL);
        when(user.getPassword()).thenReturn(DUMMY_PASSWORD);
        when(userRepository.findByEmail(DUMMY_EMAIL)).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("User registered successfully")
    public void registerUser() {
        String response = userService.registerUser(user);

        assertEquals("User registered successfully", response);
    }

    @Test
    @DisplayName("Email already exist in DB -> throws a DgsInvalidInputArgumentException")
    public void registerUser_emailAlreadyExists() {
        when(userRepository.findByEmail(DUMMY_EMAIL)).thenReturn(Optional.of(userEntity));

        assertThrows(DgsInvalidInputArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    @DisplayName("Email format is not valid -> throws a DgsInvalidInputArgumentException")
    public void registerUser_emailFormatNotValid() {
        when(user.getEmail()).thenReturn("bad$format@gs.commit");

        assertThrows(DgsInvalidInputArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    @DisplayName("Password format is not valid -> throws a DgsInvalidInputArgumentException")
    public void registerUser_passwordFormatNotValid() {
        when(user.getPassword()).thenReturn("MyPa$sword123");

        assertThrows(DgsInvalidInputArgumentException.class, () -> userService.registerUser(user));
    }
}
