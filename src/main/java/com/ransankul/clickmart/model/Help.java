package com.ransankul.clickmart.model;

import jakarta.persistence.*;

@Entity
@Table(name = "help")
public class Help {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "userEmail", nullable = false)
    private String userEmail;  

    @Column(name = "resolved")
    private boolean resolved;  

    // Constructors, getters, and setters

    public Help() {
    }


    public Help(Long id, String title, String description, String userEmail, boolean resolved) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userEmail = userEmail;
        this.resolved = resolved;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    public boolean setResolved() {
        return resolved;
    }


    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    
    
    
}

