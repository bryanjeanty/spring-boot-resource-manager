package com.resource.manager.resource.dao;

import com.resource.manager.resource.model.User;

import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Repository("fakeDao")
public class PseudoUserDataAccessService implements UserDao {
  private static List<User> DB = new ArrayList<User>();

  @Override
  public int insertUser(UUID id, User user) {
    DB.add(new User(id, user.getUsername(), user.getEmail(), user.getPassword()));
    return 1;
  }

  @Override
  public List<User> selectAllUsers() {
    return DB;
  }

  @Override
  public Optional<User> selectUser(UUID id) {
    return DB.stream().filter(user -> user.getId().equals(id)).findFirst();
  }

  @Override
  public int deleteUser(UUID id) {
    Optional<User> possibleUser = selectUser(id);
    if (possibleUser.isEmpty()) {
      return 0;
    }
    DB.remove(possibleUser.get());
    return 1;
  }

  @Override
  public int updateUser(UUID id, User newUser) {
    return selectUser(id).map(user -> {
      int indexOfUserToUpdate = DB.indexOf(user);
      if (indexOfUserToUpdate >= 0) {
        DB.set(indexOfUserToUpdate, new User(id, newUser.getUsername(), newUser.getEmail(), newUser.getPassword()));
        return 1;
      }
      return 0;
    }).orElse(0);
  }
}