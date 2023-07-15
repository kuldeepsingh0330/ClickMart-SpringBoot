package com.ransankul.clickmart.security;

import org.springframework.stereotype.Component;

@Component
public class JWTAuthResponse {

    private String token;
    private String username;
    private String msg;
    
    

    public JWTAuthResponse() {
    }

    public JWTAuthResponse(String token, String username, String msg) {
        this.token = token;
        this.username = username;
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg= msg;
    }
   
        
}
