package com.example.hw57blocks.controllers;

import com.example.hw57blocks.models.AccessToken;
import com.example.hw57blocks.models.User;
import com.example.hw57blocks.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
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
        return this.userService.registerUser(user);
    }

    @DgsQuery
    public AccessToken loginUser(@InputArgument("user") User user) throws DgsInvalidInputArgumentException {
        return this.userService.loginUser(user);
    }
}
