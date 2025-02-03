package com.example.demo.features.user;

import com.example.demo.common.jwt.AuxService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserRequestMapper userRequestMapper;
  private final AuxService auxService;

  //Llevar cuidado con estos dos metodos porque no usan el bussinesuid y por lo tanto los usuarios
  // normales no de ben usarlos.
  public User findUserbyUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("User not found by username: " + username));
  }

  public User findUserById(Integer id) {
    return userRepository.findByIdAndBusiness_BusinessUuid(id, auxService.getBussinesUUid())
        .orElseThrow(() -> new EntityNotFoundException(
            "User not found by id: " + id + " and business uuid: " + auxService.getBussinesUUid()));
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findByBusiness_BusinessUuid(auxService.getBussinesUUid());
  }

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
