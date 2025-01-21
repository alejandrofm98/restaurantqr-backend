package com.example.demo.features.user;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserRequestMapper userRequestMapper;
  private final PasswordEncoder passwordEncoder;

  public User findUserbyUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found by username: " + username));
  }

  public User findUserById(Integer id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
  }

  public List<User> findUsersByBussinesUuid(String bussinesUuid) {
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

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public void updateUser(Integer userId, UserRequest request) {
    User user = userRequestMapper.toEntity(request);
    user.setId(userId);
    saveUser(user);
  }
}
