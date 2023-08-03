package com.ransankul.clickmart.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Cookie jSessionIdCookie = new Cookie("JSESSIONID", null);
        jSessionIdCookie.setMaxAge(0);
        jSessionIdCookie.setPath("/");
        response.addCookie(jSessionIdCookie);

        Cookie jwtTokenCookie = new Cookie("JWTtoken", null);
        jwtTokenCookie.setMaxAge(0);
        jwtTokenCookie.setPath("/");
        response.addCookie(jwtTokenCookie);

        response.sendRedirect("/admin/login?logout");
    }
}