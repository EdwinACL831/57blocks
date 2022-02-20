package com.example.hw57blocks.controllers;

import com.example.hw57blocks.models.User;
import com.example.hw57blocks.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserControllerTest {
    private UserController userController;
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = mock(User.class);
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void registerUser() {
        userController.registerUser(user);

        verify(userService).registerUser(user);
    }
}
