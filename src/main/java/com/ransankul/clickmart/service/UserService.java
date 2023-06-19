package com.ransankul.clickmart.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ransankul.clickmart.model.User;

@Service
public interface UserService {
    

    //register-user
    public User registerUser(User user);

    //validte-user
    public boolean valiateUser(String userName, String password);

    //finduserByUsername
    public User finduserByUsername(String username);

    //profile picture upload
    public String uploadprofileImage(String path, MultipartFile file,String username);

}
