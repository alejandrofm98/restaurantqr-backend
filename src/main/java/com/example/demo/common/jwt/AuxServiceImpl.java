package com.example.demo.common.jwt;

import com.example.demo.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//TODO UNIFICAR CON JWTSERVICE
@Service
@RequiredArgsConstructor
public class AuxServiceImpl implements AuxService {

  private final JwtService jwtService;
  private final HttpServletRequest request;
  private final UserRepository userRepository;

  public String getUsername() {
    String token = jwtService.getTokenFromRequest(request);
    return jwtService.getUsernameFromToken(token);
  }

  public String getBussinesUUid() {
    return userRepository.findByUsername(getUsername())
        .orElseThrow(() -> new EntityNotFoundException("User not found " + getUsername()))
        .getBusiness()
        .getBusinessUuid();
  }
}
