package com.ransankul.clickmart.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ransankul.clickmart.model.User;

public interface UserRepositery extends JpaRepository<User, Integer>  {
    
    public User findByUserName(String username);
    
}
