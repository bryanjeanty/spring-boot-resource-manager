package com.resource.manager.resource.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.resource.manager.resource.dao.UserDao;
import com.resource.manager.resource.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserDao userDao;

  @Autowired
  public UserService(@Qualifier("fakeDao") UserDao userDao) {
    this.userDao = userDao;
  }

  public int addUser(User user) {
    return userDao.insertUser(user);
  }

  public List<User> getAllUsers() {
    return userDao.selectAllUsers();
  }

  public Optional<User> getUser(UUID id) {
    return userDao.selectUser(id);
  }

  public int removeUser(UUID id) {
    return userDao.deleteUser(id);
  }

  public int modifyUser(UUID id, User user) {
    return userDao.updateUser(id, user);
  }
}