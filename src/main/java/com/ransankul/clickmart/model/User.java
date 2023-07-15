package com.ransankul.clickmart.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.razorpay.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User implements UserDetails{


	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "phone_number")
    private Long phoneNumber;

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
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Wishlist> wishlist;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Transaction> orderHistory;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns=@JoinColumn(name="user",referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id")
    )
    private Set<Roles> roles = new HashSet<>();
    
    

    public Set<Roles> getRoles() {
        return roles;
    }


    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }


    public User() {
        // Default constructor required by Hibernate
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public User(int id, Long phoneNumber, String name, String password, String emailId, String profilePic,
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


    public Long getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(Long phoneNumber) {
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


    public User orElseThrow(Object object) {
        return null;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getRole()))
		.collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return this.userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    

    
    
    
}
