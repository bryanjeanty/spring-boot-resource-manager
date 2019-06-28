package com.resource.manager.resource.dao;

import com.resource.manager.resource.model.User;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface UserDao {
  int insertUser(UUID id, User user);

  default int insertUser(User user) {
    UUID id = UUID.randomUUID();
    return insertUser(id, user);
  }

  List<User> selectAllUsers();

  Optional<User> selectUser(UUID id);

  int deleteUser(UUID id);

  int updateUser(UUID id, User user);
}