package com.resource.manager.resource.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.resource.manager.resource.config.JwtProperties;
import com.resource.manager.resource.entity.Account;
import com.resource.manager.resource.authenticationmodels.*;
import com.resource.manager.resource.repository.AccountRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public AccountRepository accountRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AccountRepository accountRepository) {
        super(authenticationManager);
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Read the Authorization header, where the JWT token should be
        // Header contains "Authorization" with authorized token value as its body
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {

            // if the header is not valid, go to the next filter
            chain.doFilter(request, response);
            return;
        }

        // If there is no token provided, then the user won't be authenticated.
        // It's Ok. Maybe the user is accessing a public path or asking for a token.

        // All secured paths that needs a token are already defined and secured in
        // config class. And If user tried to access without access token, then he
        // won't be authenticated and an exception will be thrown.

        // If header is present, try grab user principal from database and perform
        // authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);

        // now, the user is authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {

        // get the authorized token from the request body
        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        if (token != null) {
            // parse the token and validate it
            String userName = JWT.require(HMAC512(JwtProperties.SECRET_TOKEN.getBytes())).build().verify(token)
                    .getSubject();

            // Check if we can find the user by token subject (username)
            // If so, then grab user details and create spring auth token using username,
            // pass, authorities/roles
            if (userName != null) {
                Account account = accountRepository.findByUsername(userName);

                AccountPrincipal principal = new AccountPrincipal(account);

                // Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object, used by spring to
                // represent the current authenticated / being authenticated user.
                // It needs a list of authorities, which has type of GrantedAuthority interface,
                // where SimpleGrantedAuthority is an implementation of that interface
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null,
                        principal.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }

    // encrypt our password with BCryptPasswordEncoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}