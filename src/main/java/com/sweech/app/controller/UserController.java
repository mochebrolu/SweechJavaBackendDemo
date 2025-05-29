package com.sweech.app.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.dto.UserDto;
import com.sweech.app.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto) {
        try {
            userService.signup(userDto);
            return ResponseEntity.ok(Map.of("id", userDto.getEmail(), "username", userDto.getUsername(), "registrationTime", userDto.getRegistrationTime()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "signup_failed", "message", e.getMessage()));
        }
    }
}
