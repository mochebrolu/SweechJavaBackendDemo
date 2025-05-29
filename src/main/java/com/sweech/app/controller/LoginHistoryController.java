package com.sweech.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.security.TokenService;

@RestController
@RequestMapping("/api/login-history")
public class LoginHistoryController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<?> getHistory(@RequestHeader("Authorization") String token) {
        String email = tokenService.getEmail(token.substring(7));
        return ResponseEntity.ok(loginService.getHistory(email));
    }
}

