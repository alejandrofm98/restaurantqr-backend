package com.example.demo.services.impl;

import com.example.demo.jwt.JwtService;
import com.example.demo.services.AuxService;
import com.example.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuxServiceImpl implements AuxService {
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
