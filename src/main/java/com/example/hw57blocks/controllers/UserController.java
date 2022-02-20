package com.example.hw57blocks.controllers;

import com.example.hw57blocks.models.User;
import com.example.hw57blocks.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class UserController {
    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @DgsMutation
    public String registerUser(@InputArgument("user") User user) throws DgsInvalidInputArgumentException {
        if (!userService.validateEmailFormat(user.getEmail().trim())) {
            throw new DgsInvalidInputArgumentException("Email format is not valid, please verify it", null);
        }

        if (userService.emailExist(user.getEmail().trim())) {
            throw new DgsInvalidInputArgumentException("Email already exist, please use a different email", null);
        }

        if (!userService.validatePasswordFormat(user.getPassword().trim())) {
            throw new DgsInvalidInputArgumentException("Password format is not valid, please verify it", null);
        }

        return user.getPassword();
    }
}
