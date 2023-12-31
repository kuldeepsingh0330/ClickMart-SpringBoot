package com.ransankul.clickmart.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.payloads.UserPayload;
import com.ransankul.clickmart.serviceImpl.UserServiceImpl;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${project.image}")
    private String path;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserPayload userpPayload) {
        User user = new User(userpPayload.getUserId(), userpPayload.getPhoneNumber(), userpPayload.getName(), userpPayload.getPassword(), userpPayload.getEmailId(),"",userpPayload.getUserName(), null);  	
        User createdUser = userServiceImpl.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestParam String username, @RequestParam String password) {
        boolean isValid = userServiceImpl.valiateUser(username, password);
        if (isValid) {
            return ResponseEntity.ok("User is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }
    
}