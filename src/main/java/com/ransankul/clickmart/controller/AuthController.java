package com.ransankul.clickmart.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ransankul.clickmart.model.APIResponse;
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
    public ResponseEntity<JWTAuthResponse> login(@RequestBody Map<String,String> map){

    	String username = map.get("username");
    	String password = map.get("password");
        this.doAuthenticate(username,password);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JWTAuthResponse response = new JWTAuthResponse(token,userDetails.getUsername(),"Login succesfully");
        
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<APIResponse<String>> validateToken(@RequestBody Map<String,String> map){
    	System.out.println("validate token");
    	String token = map.get("token");
    	if(!jwtTokenHelper.isTokenExpired(token)) 
    		return new ResponseEntity<>(new APIResponse<>("200","","token is still valid"),HttpStatus.OK);
    	else
    		return new ResponseEntity<>(new APIResponse<>("400","","token is expired"),HttpStatus.BAD_REQUEST);
    		
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
