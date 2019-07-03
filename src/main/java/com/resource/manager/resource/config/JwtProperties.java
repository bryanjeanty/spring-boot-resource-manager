package com.resource.manager.resource.config;

// The class defines all the constant properties required 
// for our Jwt authentication
public final class JwtProperties {

    // we can change this as per requirements
    public static final String SECRET_TOKEN = "ResourceManager";

    // currently approximately 10 days
    public static final int EXPIRATION_TIME = 864000000;

    // token and authentication tags for our response header
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}