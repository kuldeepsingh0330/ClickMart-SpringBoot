package com.ransankul.clickmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.serviceImpl.UserServiceImpl;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User createdUser = userServiceImpl.registerUser(user);
        return ResponseEntity.ok("User registered successfully with ID: " + createdUser.getId());
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestParam String username, @RequestParam String password) {
        
        System.out.println("-------------------------------------------+++");
        boolean isValid = userServiceImpl.valiateUser(username, password);
        if (isValid) {
            return ResponseEntity.ok("User is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

    
}
