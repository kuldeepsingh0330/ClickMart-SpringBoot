package com.ransankul.clickmart.repositery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ransankul.clickmart.model.User;

@Repository
public interface UserRepositery extends JpaRepository<User, Integer>  {

    public User findByUserName(String username);
    
}
