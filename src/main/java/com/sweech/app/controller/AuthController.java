package com.sweech.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.dto.ErrorResponse;
import com.sweech.app.dto.LoginRequest;
import com.sweech.app.dto.LoginResponse;
import com.sweech.app.dto.RegisterRequest;
import com.sweech.app.dto.RegisterResponse;
import com.sweech.app.model.User;
import com.sweech.app.service.AuthService;
import com.sweech.app.service.LoginRecordService;
import com.sweech.app.service.UserService;
import com.sweech.app.util.IpAddressFinder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    private LoginRecordService loginRecordService;
    
    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> signup(@RequestBody RegisterRequest request) {
        try {
            User user = authService.registerUser(request.getEmail(), request.getPassword(), request.getUsername());

            RegisterResponse response = new RegisterResponse();
            response.setEmail(user.getEmail());
            response.setUsername(user.getUsername());
            response.setCreatedAt(user.getRegisteredAt());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse("registration_failed", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("server_error", "An unexpected error occurred")
            );
        }
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,HttpServletRequest httpRequest) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            User user = userService.findByEmail(request.getEmail());
            loginRecordService.recordLogin(user.getId(), IpAddressFinder.getClientIpAddress(httpRequest));
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponse("login_failed", e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse("server_error", "An unexpected error occurred")
            );
        }
    }


}
