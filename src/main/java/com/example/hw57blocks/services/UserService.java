package com.example.hw57blocks.services;

import com.example.hw57blocks.entities.UserEntity;
import com.example.hw57blocks.models.User;
import com.example.hw57blocks.repositories.UserRepository;
import com.example.hw57blocks.utils.Util;
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final static String REGISTRATION_DONE_MSG = "User registered successfully";
    private final static String EMAIL_EXISTS_MSG = "Email already exist, please use a different email";
    private final static String INVALID_EMAIL_FORMAT_MSG = "Email format is not valid, please verify it";
    private final static String INVALID_PASSWORD_FORMAT_MSG = "Password format is not valid, please verify it";

    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean emailExist(String email) {
        Optional<UserEntity> result = userRepository.findByEmail(email);
        return result.isPresent();
    }

    public String registerUser(User user) throws DgsInvalidInputArgumentException {
        String email = user.getEmail().trim();
        String password = user.getPassword().trim();

        if (!Util.isValidEmail(email)) {
            throw new DgsInvalidInputArgumentException(INVALID_EMAIL_FORMAT_MSG, null);
        }

        if (emailExist(email)) {
            throw new DgsInvalidInputArgumentException(EMAIL_EXISTS_MSG, null);
        }

        if (!Util.isValidPassword(password)) {
            throw new DgsInvalidInputArgumentException(INVALID_PASSWORD_FORMAT_MSG, null);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userRepository.save(userEntity);

        return REGISTRATION_DONE_MSG;
    }
}
