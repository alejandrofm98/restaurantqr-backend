package com.example.demo.services.impl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public User findUserbyUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found by username: " + username));
  }

  public User findUserById(Integer id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
  }

  public List<User> findUsersByBussinesUuid (String bussinesUuid) {
    return userRepository.findByBusiness_BusinessUuid(bussinesUuid);
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
