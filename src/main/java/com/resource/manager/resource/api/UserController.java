package com.resource.manager.resource.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.resource.manager.resource.model.User;
import com.resource.manager.resource.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public void addUser(@Valid @NotNull @RequestBody User user) {
    userService.addUser(user);
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping(path = "{id}")
  public User getUser(@PathVariable("id") UUID id) {
    return userService.getUser(id).orElse(null);
  }

  @DeleteMapping(path = "{id}")
  public void removeUser(@PathVariable("id") UUID id) {
    userService.removeUser(id);
  }

  @PutMapping(path = "{id}")
  public void modifyUser(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody User user) {
    userService.modifyUser(id, user);
  }
}