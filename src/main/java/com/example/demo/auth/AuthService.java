package com.example.demo.auth;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterBusinessOwnerRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.request.mapper.BusinessRequestMapper;
import com.example.demo.dto.request.mapper.UserRequestMapper;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.RegisterBusinessOwnerResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.mapper.BusinessResponseMapper;
import com.example.demo.dto.response.mapper.UserResponseMapper;
import com.example.demo.entity.Business;
import com.example.demo.entity.User;
import com.example.demo.exception.Exceptions;
import com.example.demo.jwt.JwtService;
import com.example.demo.services.AuxService;
import com.example.demo.services.BussinesService;
import com.example.demo.services.RolService;
import com.example.demo.services.UserService;
import com.example.demo.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Log4j2
@Service
public class AuthService {


  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserResponseMapper userResponseMapper;
  private final BusinessResponseMapper businessResponseMapper;
  private final RolService rolService;
  private final UserService userService;
  private final BussinesService bussinesService;
  private final BusinessRequestMapper businessRequestMapper;
  private final UserRequestMapper userRequestMapper;
  private final AuxService auxService;

  @Transactional
  public AuthResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userService.findUserbyUsername(userDetails.getUsername());

    // Actualizar el campo fcmToken
    user.setFcmToken(request.getFcmToken());
    userService.saveUser(user);

    String token = jwtService.getToken(user);

    UserResponse response = userResponseMapper.toDto(user);

    return AuthResponse.builder()
        .token(token)
        .user(response)
        .build();
  }

  @Transactional
  public AuthResponse register(UserRequest request) {
    request.setBusinessUuid(auxService.getBussinesUUid());
    checkForDuplicatesUser(request);

    User user = userService.saveUser(userRequestMapper.toEntity(request));

    return AuthResponse.builder()
        .token(jwtService.getToken(user))
        .user(userResponseMapper.toDto(user))
        .build();
  }

  @Transactional
  public RegisterBusinessOwnerResponse registerBusinessAndOwner(
      RegisterBusinessOwnerRequest request) {

    checkForDuplicatesUser(request.getUserRequest());

    Business business = bussinesService.saveBusiness(
        businessRequestMapper.toEntity(request.getBusinessRequest()));

    request.getUserRequest().setRole(Constants.ROL_OWNER);
    request.getUserRequest().setBusinessUuid(business.getBusinessUuid());

    User user = userService.saveUser(userRequestMapper.toEntity(request.getUserRequest()));
    return RegisterBusinessOwnerResponse.builder()
        .token(jwtService.getToken(user))
        .user(userResponseMapper.toDto(user))
        .business(businessResponseMapper.toDto(business))
        .build();
  }

  private void checkForDuplicatesUser(UserRequest request) {
    if (userService.existsByUsername(request.getUsername())) {
      throw new Exceptions("The user name is already in use.");
    }

    if (userService.existsByEmail(request.getEmail())) {
      throw new Exceptions("The email address is already in use.");
    }

  }


  public void updateUser(Integer userId, UserRequest request) {
    User user = userService.findUserById(userId);
    user.setName(request.getName());
    user.setLastname(request.getLastname());
    user.setEmail(request.getEmail());
    user.setRol(rolService.findByRolName(request.getRole()));
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setStatus(request.getStatus());
    if (!bussinesService.existsBusinessById(request.getBusinessUuid())) {
      throw new Exceptions("The business does not exist");
    }
    user.setBusiness(bussinesService.findBusinessById(request.getBusinessUuid()));
    userService.saveUser(user);
  }


}
