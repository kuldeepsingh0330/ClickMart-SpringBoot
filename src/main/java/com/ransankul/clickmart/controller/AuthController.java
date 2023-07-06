package com.ransankul.clickmart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.model.TokenResponse;
import com.ransankul.clickmart.security.JWTAuthResponse;
import com.ransankul.clickmart.security.JWTTokenHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam String username, @RequestParam String password){

        this.doAuthenticate(username,password);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = this.jwtTokenHelper.generateToken(userDetails);
        String response = new JWTAuthResponse(token,userDetails.getUsername()).toString();
        
        return new ResponseEntity<>(new TokenResponse(response,"Login succesfully"),HttpStatus.OK);
    }


    private void doAuthenticate(String username, String password){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,password);
        try{
            manager.authenticate(authentication);
        }catch(BadCredentialsException e){
            throw new RuntimeException("Invalid username and password");
        }
    }
    
}
