package com.ransankul.clickmart.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.repositery.UserRepositery;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositery userRepositery;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {

        User user = this.userRepositery.findByUserName(username);
        if(user == null){
            throw new ResourceNotFoundException("User not found with thid username "+username); 
        }   

        return user;
    }
}