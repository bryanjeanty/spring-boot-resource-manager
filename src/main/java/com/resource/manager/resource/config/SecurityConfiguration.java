package com.resource.manager.resource.config;

import java.util.Collections;
import java.util.Arrays;

import com.resource.manager.resource.repository.AccountRepository;
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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AccountDetailsService accountDetailsService;
    private AccountRepository accountRepository;

    public SecurityConfiguration(AccountDetailsService accountDetailsService, AccountRepository accountRepository) {
        this.accountDetailsService = accountDetailsService;
        this.accountRepository = accountRepository;
    }

    // encode password using BCrypt method with given
    // strength (the log rounds to use)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {  
    	/* Allow cors */
    	 http.cors().and()
    	
        // Allow only authorized users to make requests
        // to other pages using JWT based authentication

        // we remove CSRF and state in session because we do not require
        // them in JWT authentication
        .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // authorization requests configuration
                // allow any users to access only login and home pages
                .authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/api/v1/login**").permitAll()
                .antMatchers("/api/v1/register**").permitAll()

                // only authenticated users can access other routes in the application
                .antMatchers("/api/v1/auth/**").authenticated()

                // all other url requests need to be authenticated
                .anyRequest().authenticated().and()
                
                //custom login
                .formLogin().loginPage("/api/v1/login").permitAll().and()

                // add JWT filters (1. authentication, 2. authorization) to validate
                // tokens with every request
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.accountRepository))

                // redirect the user to the login page if they fail to authenticate
                .exceptionHandling().accessDeniedPage("/api/v1/login");
    }
    
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		config.setAllowedHeaders(Arrays.asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		
		source.registerCorsConfiguration("/**", config);
        
        return source;
    }

    // create a bean to encode the user details for authentication purposes
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.accountDetailsService);

        return daoAuthenticationProvider;
    }
}