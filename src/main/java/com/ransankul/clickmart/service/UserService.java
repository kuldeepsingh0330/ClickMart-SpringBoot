package com.ransankul.clickmart.service;


import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.User;

@Service
public interface UserService {
    

    //register-user
    public User registerUser(User user);

    //validte-user
    public boolean valiateUser(String userName, String password);

}
