package com.example.demo.auth;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.dto.response.mapper.BusinessResponseMapper;
import com.example.demo.dto.response.mapper.UserResponseMapper;
import com.example.demo.entity.Business;
import com.example.demo.entity.Rol;
import com.example.demo.entity.User;
import com.example.demo.exception.Exceptions;
import com.example.demo.jwt.JwtService;
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

    Business business = bussinesService.findBusinessById(user.getBusiness().getBusinessUuid());

    String updatedAt = business.getUpdatedAt() != null ? business.getUpdatedAt().toString() : null;

    BusinessResponse businessResponse = businessResponseMapper.toDto(business);
    businessResponse.setUpdatedAt(updatedAt);

    return AuthResponse.builder()
        .token(token)
        .user(response)
        .business(businessResponse)
        .build();
  }


  public AuthResponse register(RegisterRequest request) {

    checkForDuplicates(request);

    Rol ownerRol = rolService.findByRolName(Constants.ROL_OWNER);
    Integer rol = request.getRol() != null ? request.getRol() : ownerRol.getId();
    //TODO: COMPROBAR QUE TENGA PERMISOS PARA PONERLE EL ROL INDICADO COMPROBANDO EL BUSSINESUID DE
    // LA REQUEST
    User user = User.builder().username(request.getUsername())
        .name(request.getName())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .username(request.getUsername())
        .rol(rolService.findById(rol))
        .business(bussinesService.findBusinessById(request.getBusinessUuid()))
        .status(true)
        .password(passwordEncoder.encode(request.getPassword()))
        .fcmToken(request.getFcmToken())
        .build();

    userService.saveUser(user);

    String token = jwtService.getToken(user);

    UserResponse response = userResponseMapper.toDto(user);

    Business business = bussinesService.findBusinessById(user.getBusiness().getBusinessUuid());

    String updatedAt = business.getUpdatedAt() != null ? business.getUpdatedAt().toString() : null;

    BusinessResponse businessResponse = businessResponseMapper.toDto(business);

    businessResponse.setUpdatedAt(updatedAt);

    return AuthResponse.builder()
        .token(token)
        .user(response)
        .business(businessResponse)
        .build();


  }

  private void checkForDuplicates(RegisterRequest request) {
    if (userService.existsByUsername(request.getUsername())) {
      throw new Exceptions("The user name is already in use.");
    }

    if (userService.existsByEmail(request.getEmail())) {
      throw new Exceptions("The email address is already in use.");
    }

    if (!bussinesService.existsBusinessById(request.getBusinessUuid())) {
      throw new Exceptions("The business does not exist");
    }
  }


  public void updateUser(Integer userId, RegisterRequest request) {
    User user = userService.findUserById(userId);
    user.setName(request.getName());
    user.setLastname(request.getLastname());
    user.setEmail(request.getEmail());
    user.setRol(rolService.findById(request.getRol()));
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setStatus(request.getStatus());
    if (!bussinesService.existsBusinessById(request.getBusinessUuid())) {
      throw new Exceptions("The business does not exist");
    }
    user.setBusiness(bussinesService.findBusinessById(request.getBusinessUuid()));
    userService.saveUser(user);
  }


}
