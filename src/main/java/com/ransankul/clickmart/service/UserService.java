package com.ransankul.clickmart.service;


import com.ransankul.clickmart.model.User;


public interface UserService {
    

    //register-user
    public User registerUser(User user);

    //validte-user
    public boolean valiateUser(String userName, String password);

}
