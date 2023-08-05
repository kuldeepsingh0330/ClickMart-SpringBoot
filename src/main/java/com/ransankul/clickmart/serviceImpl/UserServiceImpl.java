package com.ransankul.clickmart.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ransankul.clickmart.model.Roles;
import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.repositery.UserRepositery;
import com.ransankul.clickmart.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositery userRepositery;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(User user) {
    	Set<Roles> set = new HashSet<>();
    	set.add(new Roles(2,"ROLE_NORMAL_USER"));
    	user.setRoles(set);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); 
        User createdUser = userRepositery.save(user);
        return createdUser;
    }

    @Override
    public boolean valiateUser(String userName, String password) {
        
        User user = userRepositery.findByUserName(userName);
        if (user != null) {
            return bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    @Override
    public String uploadprofileImage(String path, MultipartFile file, String username) {
    try {
        String originalName = file.getOriginalFilename();
        if(originalName == null) return null;
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
