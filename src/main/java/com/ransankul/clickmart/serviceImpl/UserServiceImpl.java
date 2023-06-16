package com.ransankul.clickmart.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.repositery.UserRepositery;
import com.ransankul.clickmart.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositery userRepositery;

    @Override
    public User registerUser(User user) {
        User createdUser = userRepositery.save(user);
        return createdUser;
    }

    @Override
    public boolean valiateUser(String userName, String password) {
        
        // System.out.println("-------------------------------------------");
        User user = userRepositery.findByUserName(userName);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    
}
