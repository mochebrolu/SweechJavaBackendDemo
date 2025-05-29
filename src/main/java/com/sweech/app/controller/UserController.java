package com.sweech.app.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sweech.app.dto.ErrorResponse;
import com.sweech.app.dto.UserDto;
import com.sweech.app.model.User;
import com.sweech.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // User registration - no authentication required
    @PostMapping(value = "/signup", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto request) {
        try {
            User user = userService.registerUser(request.getEmail(), request.getPassword(), request.getUsername());

            UserDto response = new UserDto();
            response.setEmail(user.getEmail());
            response.setUsername(user.getUsername());
            response.setRegistrationTime(user.getRegisteredAt());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("registration_failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ErrorResponse("server_error", "Unexpected error"));
        }
    }

    // User update - requires authentication (token-based)
    @PatchMapping(value = "/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUser(
        @PathVariable Long userId,
        @RequestBody UserDto request
    ) {
        try {
            // Only update non-null fields
            userService.updateUser(userId, request.getPassword(), request.getUsername());

            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ErrorResponse("update_failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ErrorResponse("server_error", "Unexpected error"));
        }
    }

   
 
}



