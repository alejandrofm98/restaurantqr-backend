package com.example.demo.common.jwt;

import com.example.demo.features.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//TODO UNIFICAR CON JWTSERVICE
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
    return userService.findUserbyUsername(getUsername())
        .getBusiness().getBusinessUuid();
  }
}
