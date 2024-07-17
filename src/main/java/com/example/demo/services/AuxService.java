package com.example.demo.services;

import com.example.demo.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuxService {
  private final JwtService jwtService;
  private final HttpServletRequest request;
  private final UserService userService;

  public String getUsername() {
    String token = jwtService.getTokenFromRequest(request);
    return jwtService.getUsernameFromToken(token);
  }

  public String getBussinesUUid(){
    return userService.findUserbyUser(getUsername())
        .getBusinessUuid();
  }
}
