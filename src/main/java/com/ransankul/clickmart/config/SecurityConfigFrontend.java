package com.ransankul.clickmart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.security.CustomUserDetailsService;
import com.ransankul.clickmart.security.JWTAuthFilter;
import com.ransankul.clickmart.security.JWTauthEntryPoint;


@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfigFrontend {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    
	    http.securityMatcher("/admin/**").csrf(csrf-> csrf.disable())
        .authorizeHttpRequests(auth-> auth
        		.requestMatchers("/admin/login").permitAll()
        		.requestMatchers("/admin/redirect").permitAll()
        		.requestMatchers("/admin/login/token").permitAll()
        		.requestMatchers("/admin/**").authenticated()
        		.anyRequest().authenticated())
                .formLogin((form) -> form
				.loginPage("/admin/login")
                .loginProcessingUrl("/admin/login/token")
                .defaultSuccessUrl("/admin/category")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

        return http.build();
	}

    



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

}