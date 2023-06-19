package com.ransankul.clickmart.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public String uploadprofileImage(String path, MultipartFile file, String username) {
    try {
        String originalName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String finalName = randomId + originalName.substring(originalName.lastIndexOf("."));

        String filePath = path + File.separator + finalName;

        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        User user = userRepositery.findByUserName(username);
        user.setProfilePic(finalName);
        userRepositery.save(user);


        return originalName;
    } catch (IOException e) {
        return null;
    }
    }

    @Override
    public User finduserByUsername(String username) {
        User user = userRepositery.findByUserName(username);
        return user;     

    }

    
    
}
