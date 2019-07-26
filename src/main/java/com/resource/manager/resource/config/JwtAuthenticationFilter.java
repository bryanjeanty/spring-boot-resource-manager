package com.resource.manager.resource.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resource.manager.resource.config.JwtProperties;
import com.resource.manager.resource.authenticationmodels.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    // if the login page is not /login, then we need to override defaults
    // refer to
    // https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8
    // for more details
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v2/login", "POST"));
    }

    // the method is called when a POST request is issued to /login
    // need to pass in {"username":"admin", "password":"admin"} in the request body
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // grab authentication credentials and then map to AuthenticationViewModel
        AuthenticationViewModel credentials = null;

        try {
            // get credentials from request
            credentials = new ObjectMapper().readValue(request.getInputStream(), AuthenticationViewModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create JWT login token
        // the token is not returned to the user
        // Spring Security uses this token to authenticate by using
        // token provided by us
        // if we have defined roles/authorities in our application, then we need to
        // change Collections.emptyList() accoordingly
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword(), Collections.emptyList());

        // Authentication manager authenticates the user, and uses
        // AccountDetailsService::loadUserByUsername() method to load the user.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current
    // authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // grab Account Principal
        AccountPrincipal principal = (AccountPrincipal) authResult.getPrincipal();

        // Create JWT token
        // Set the token to expire after certain period as defined in the constant
        // property hash the token using HMAC512 algorithm
        String token = JWT.create().withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET_TOKEN.getBytes()));

        // expose jwt token on client side
        response.addHeader("Access-Control-Expose-Headers", JwtProperties.HEADER_STRING);
        
        // add token in the response header
        // user will use this token to access other routes in the application
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
    }
}