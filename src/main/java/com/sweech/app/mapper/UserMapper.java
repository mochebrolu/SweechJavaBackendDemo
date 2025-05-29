package com.sweech.app.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sweech.app.dto.UserDto;
import com.sweech.app.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserDto userDto) {
        // Validate and encode password
        if (!isValidPassword(userDto.getPassword())) {
            throw new IllegalArgumentException("Invalid password format");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRegistrationTime(Instant.now().toString());

        // Check for duplicate email
        if (userMapper.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Insert user into database
        userMapper.insert(userDto);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 12 && password.length() <= 20 &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*].*");
    }
}
