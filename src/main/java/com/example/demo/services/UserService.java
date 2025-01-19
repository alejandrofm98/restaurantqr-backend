package com.example.demo.services;

import com.example.demo.entity.User;
import java.util.List;


public interface UserService {

  User findUserbyUsername(String username);

  User findUserById(Integer id);

  User saveUser(User user);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  List<User> findUsersByBussinesUuid(String bussinesUuid);

  List<User> findAllUsers();
}
