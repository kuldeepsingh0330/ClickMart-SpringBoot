package com.ransankul.clickmart.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Address> userAddress;

    public User() {
        // Default constructor required by Hibernate
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public User(int id, int phoneNumber, String name, String password, String emailId, String profilePic,
            String userName, List<Address> userAddress) {
        this.userId = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
        this.emailId = emailId;
        this.profilePic = profilePic;
        this.userName = userName;
        this.userAddress = userAddress;
    }


    public int getId() {
        return userId;
    }


    public void setId(int id) {
        this.userId = id;
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


    public List<Address> getUserAddress() {
        return userAddress;
    }


    public void setUserAddress(List<Address> userAddress) {
        this.userAddress = userAddress;
    }


    

    
    
    
}
