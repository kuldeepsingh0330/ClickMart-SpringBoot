package com.ransankul.clickmart.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ransankul.clickmart.model.User;
import com.ransankul.clickmart.serviceImpl.UserServiceImpl;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${project.image}")
    private String path;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {   	
        User createdUser = userServiceImpl.registerUser(user);
        return ResponseEntity.ok("User registered successfully with ID: " + createdUser.getId());
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestParam String username, @RequestParam String password) {
        boolean isValid = userServiceImpl.valiateUser(username, password);
        if (isValid) {
            return ResponseEntity.ok("User is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
    }

    @PostMapping("/upload_profile_picture/{username}")
    public ResponseEntity<String> uploadImage(@RequestParam("profile_picture") MultipartFile file, @PathVariable("username") String username ) {

        User user = userServiceImpl.finduserByUsername(username);
        String fname = "";
        if(user != null){

            try{    
                fname = userServiceImpl.uploadprofileImage(path, file,username);
                return ResponseEntity.ok("Image uploaded succesfully."+fname);
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");                
            }

        }

        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please check the user name");
    
    }

    @GetMapping("/profile_image/{username}")
    public ResponseEntity<Resource> serveImage(@PathVariable String username) throws IOException {

        User user =  userServiceImpl.finduserByUsername(username);
        if(user != null){
            String folderPath = path;
            Path imagePath = Paths.get(folderPath).resolve(user.getProfilePic());
            Resource imageResource = new UrlResource(imagePath.toUri());

            if (imageResource.exists() && imageResource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); 
            
                return ResponseEntity.ok().headers(headers).body(imageResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        }else
            return ResponseEntity.badRequest().build();
    }

    
}