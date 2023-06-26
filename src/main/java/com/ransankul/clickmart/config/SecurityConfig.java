package com.ransankul.clickmart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ransankul.clickmart.exception.ResourceNotFoundException;
import com.ransankul.clickmart.security.CustomUserDetailsService;
import com.ransankul.clickmart.security.JWTAuthFilter;
import com.ransankul.clickmart.security.JWTauthEntryPoint;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig implements AuthenticationProvider {


    @Autowired
    private JWTAuthFilter jwtauthFilter;
    
    @Autowired
    private JWTauthEntryPoint jwTauthEntryPoint;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String[] PUBLIC_URL = {
        "/auth/**","/v3/api-docs","/v2/api-docs","/swagger-resources/**","/swagger-ui/**","webjars/**"
    };


    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    
	    http.csrf(csrf-> csrf.disable()).cors(cors-> cors.disable())
        .authorizeHttpRequests(auth-> auth.requestMatchers(PUBLIC_URL).permitAll().anyRequest().authenticated())
        .exceptionHandling(ex->ex.authenticationEntryPoint(jwTauthEntryPoint))
        .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtauthFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
	}


	// Authenticate the requested user
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        System.out.println(password +"     "+userDetails.getPassword());
        if (userDetails.getPassword().equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        } else {
            throw new ResourceNotFoundException("incorrect password");
        }
    }



    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }    

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception{
        return builder.getAuthenticationManager();
    }

}

