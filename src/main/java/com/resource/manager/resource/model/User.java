package com.resource.manager.resource.model;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
  private final UUID id;

  @NotBlank
  private final String username;

  @NotBlank
  private final String email;

  @NotBlank
  private final String password;

  public User(@JsonProperty("id") UUID id, @JsonProperty("username") String username,
      @JsonProperty("email") String email, @JsonProperty("password") String password) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}