package com.resource.manager.resource.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resource.manager.resource.config.JwtProperties;
import com.resource.manager.resource.controller.AccountPrincipal;
import com.resource.manager.resource.controller.AuthenticationViewModel;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // the method is called when a POST request is issued to /login
    // need to pass in {"username":"admin", "password":"admin"} in the request body
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // grab authentication credentials and then map to AuthenticationViewModel
        AuthenticationViewModel credentials = null;

        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), AuthenticationViewModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // create JWT login token
        // the token is not returned to the user
        // Spring Security uses this token to authenticate by using
        // token provided by us
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword(), new ArrayList<>());

        // authenticate the user
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        // grab Account Principal
        AccountPrincipal principal = (AccountPrincipal) authResult.getPrincipal();

        // Create JWT token
        String token = JWT.create().withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET_TOKEN.getBytes()));

        // add token in the response
        // user will use this token to access other routes in the application
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
    }
}