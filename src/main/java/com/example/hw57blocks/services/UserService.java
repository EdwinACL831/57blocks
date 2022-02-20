package com.example.hw57blocks.services;

import com.example.hw57blocks.entities.UserEntity;
import com.example.hw57blocks.repositories.UserRepository;
import com.example.hw57blocks.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean validateEmailFormat(String email) {
        return Util.isValidEmail(email);
    }

    public boolean validatePasswordFormat(String password) {
        return Util.isValidPassword(password);
    }

    public boolean emailExist(String email) {
        Optional<UserEntity> result = userRepository.findByEmail(email);
        return result.isPresent();
    }
}
