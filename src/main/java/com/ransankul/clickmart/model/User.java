package com.ransankul.clickmart.model;

import java.util.ArrayList;

public class User {

    int id,phoneNumber;
    String name, password, emailId, profilePic;
    String userName;
    ArrayList<Address> userAddress;


    public User(int id, int phoneNumber, String name, String password, String emailId, String profilePic,
            String userName, ArrayList<Address> userAddress) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.emailId = emailId;
        this.profilePic = profilePic;
        this.userName = userName;
        this.userAddress = userAddress;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmailId() {
        return emailId;
    }


    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


    public String getProfilePic() {
        return profilePic;
    }


    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public ArrayList<Address> getUserAddress() {
        return userAddress;
    }


    public void setUserAddress(ArrayList<Address> userAddress) {
        this.userAddress = userAddress;
    }


    

    
    
    
}
