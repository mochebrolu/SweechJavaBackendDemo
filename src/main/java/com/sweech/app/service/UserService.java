package com.sweech.app.service;


import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sweech.app.mapper.UserMapper;
import com.sweech.app.model.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String email, String rawPassword, String username) {
        if (userMapper.findByEmail(email)!=null) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRegisteredAt(Instant.now().toString());
        userMapper.insert(user);
        return user;
    }

    public void updateUser(Long userId, String newPassword, String newUsername) {
        User existing = userMapper.findById(userId);
        if (existing == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (newPassword != null) {
            existing.setPassword(passwordEncoder.encode(newPassword));
        }
        if (newUsername != null) {
            existing.setUsername(newUsername);
        }
        userMapper.update(existing);
    }

    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public User findById(Long id) {
        return userMapper.findById(id);
    }
}
