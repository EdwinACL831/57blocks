package com.example.hw57blocks.services;

import com.example.hw57blocks.entities.UserEntity;
import com.example.hw57blocks.models.AccessToken;
import com.example.hw57blocks.models.User;
import com.example.hw57blocks.repositories.UserRepository;
import com.example.hw57blocks.utils.EmailUtil;
import com.example.hw57blocks.utils.JWTUtil;
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final static String REGISTRATION_DONE_MSG = "User registered successfully";
    private final static String EMAIL_EXISTS_MSG = "Email already exists, please use a different email";
    private final static String EMAIL_NOT_EXISTS_MSG = "Email does not exist, please use a registered email";
    private final static String INVALID_EMAIL_FORMAT_MSG = "Email format is not valid, please verify it";
    private final static String INVALID_PASSWORD_FORMAT_MSG = "Password format is not valid, please verify it";
    private final static String INVALID_CREDENTIALS = "Invalid credentials!, please verify the email and password";

    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean emailExist(String email) {
        Optional<UserEntity> result = userRepository.findByEmail(email);
        return result.isPresent();
    }

    private void shouldEmailExist(String email, boolean shouldExist) throws DgsInvalidInputArgumentException {
        if (shouldExist && !emailExist(email)) {
            throw new DgsInvalidInputArgumentException(EMAIL_NOT_EXISTS_MSG, null);
        } else if (!shouldExist && emailExist(email)) {
            throw new DgsInvalidInputArgumentException(EMAIL_EXISTS_MSG, null);
        }
    }

    private void shouldPasswordExist(User user, boolean shouldExist) {
        String password = user.getPassword().trim();
        if (!shouldExist && !EmailUtil.isValidPassword(password)) {
            throw new DgsInvalidInputArgumentException(INVALID_PASSWORD_FORMAT_MSG, null);
        }

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail().trim());
        UserEntity storedUser = userEntity.orElse(null);

        if (shouldExist && null != storedUser && !storedUser.getPassword().equals(password)) {
            throw new DgsInvalidInputArgumentException(INVALID_CREDENTIALS, null);
        }
    }

    private void validateEmailAndPassword(User user, boolean shouldUserExist) throws DgsInvalidInputArgumentException {
        String email = user.getEmail().trim();

        if (!EmailUtil.isValidEmail(email)) {
            throw new DgsInvalidInputArgumentException(INVALID_EMAIL_FORMAT_MSG, null);
        }

        shouldEmailExist(email, shouldUserExist);
        shouldPasswordExist(user, shouldUserExist);
    }

    public String registerUser(User user) throws DgsInvalidInputArgumentException {
        validateEmailAndPassword(user,false );

        UserEntity userEntity = new UserEntity(user.getEmail(), user.getPassword());
        userRepository.save(userEntity);

        return REGISTRATION_DONE_MSG;
    }

    public AccessToken loginUser(User user) throws DgsInvalidInputArgumentException {
        validateEmailAndPassword(user,true);

        long lifeTimeMillis = (1000L * 60L * 20L);

        return new AccessToken(JWTUtil.generateToken(Map.of(JWTUtil.JWT_EMAIL_KEY, user.getEmail().trim()), lifeTimeMillis));
    }
}
