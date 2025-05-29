package com.sweech.app.service;

import java.time.Instant;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sweech.app.mapper.UserMapper;
import com.sweech.app.model.User;
import com.sweech.app.security.JwtTokenProvider;
import com.sweech.app.security.UserPrincipal;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PASSWORD_PATTERN =
        Pattern.compile("^(?=.*[a-z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{12,20}$");

    private static final Pattern USERNAME_PATTERN =
        Pattern.compile("^[가-힣]{1,10}$");

    public User registerUser(String email, String password, String username) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (userMapper.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("Password must be 12-20 characters with lowercase, number, special char");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("Username must be Korean 1-10 characters");
        }

        String encodedPassword = passwordEncoder.encode(password);
        String registrationTime = Instant.now().toString();

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setUsername(username);
        user.setRegisteredAt(registrationTime);

        userMapper.insert(user);
        return user;
    }

    public String login(String email, String password) {
        // Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return jwtTokenProvider.generateToken(userPrincipal);
    }
}
