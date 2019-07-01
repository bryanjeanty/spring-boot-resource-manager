package com.resource.manager.resource.config;

import com.resource.manager.resource.repository.AccountRepository;
import com.resource.manager.resource.repository.JwtAuthenticationFilter;
import com.resource.manager.resource.repository.JwtAuthorizationFilter;
import com.resource.manager.resource.service.AccountDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AccountDetailsService accountDetailsService;
    private AccountRepository accountRepository;

    public SecurityConfiguration(AccountDetailsService accountDetailsService, AccountRepository accountRepository) {
        this.accountDetailsService = accountDetailsService;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Allow only authorized users to make requests
        // to other pages using JWT based authentication
        // we remove CSRF and state in session because we do not require
        // them in JWT authentication
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // add JWT filters (1. authentication, 2. authorization)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.accountRepository))
                .authorizeRequests().antMatchers("/login").permitAll().antMatchers("/api/**").authenticated()
                .antMatchers("/accounts/**").authenticated();
    }

    // encode password using BCrypt method
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder;
    }

    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.accountDetailsService);

        return daoAuthenticationProvider;
    }
}