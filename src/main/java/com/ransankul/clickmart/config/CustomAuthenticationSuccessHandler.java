package com.ransankul.clickmart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ransankul.clickmart.security.JWTTokenHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String username = request.getParameter("username");

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        String token = this.jwtTokenHelper.generateToken(userDetails);
        Cookie c = new Cookie("JWTtoken", token);
        c.setMaxAge(-1);
        c.setPath("/");
        response.addCookie(c);

        response.sendRedirect("/admin/category");
    }

}
