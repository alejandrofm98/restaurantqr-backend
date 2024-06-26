package com.example.demo.auth;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.BusinessResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.Business;
import com.example.demo.entity.User;
import com.example.demo.exception.Exceptions;
import com.example.demo.jwt.JwtService;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class AuthService {

  private final UserRepository userRepository;
  private final BusinessRepository businessRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public AuthResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

    // Actualizar el campo fcmToken
    user.setFcmToken(request.getFcmToken());
    userRepository.save(user);

    String token = jwtService.getToken(user);

    UserResponse response = UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .lastname(user.getLastname())
        .name(user.getName())
        .username(user.getUsername())
        .status(user.getStatus())
        .rol(user.getRol())
        .businessUuid(user.getBusinessUuid())
        .build();

    Business business = businessRepository.findByBusinessUuid(user.getBusinessUuid()).orElseThrow();

    String updatedAt = business.getUpdatedAt() != null ? business.getUpdatedAt().toString() : null;

    BusinessResponse businessResponse = BusinessResponse.builder()
        .businessUuid(business.getBusinessUuid())
        .name(business.getName())
        .address(business.getAddress())
        .country(business.getCountry())
        .documentNumber(business.getDocumentNumber())
        .documentType(business.getDocumentType())
        .lenguajeIso2(business.getLenguajeIso2())
        .lenguajeIso3(business.getLenguajeIso3())
        .postalCode(business.getPostalCode())
        .province(business.getProvince())
        .state(business.getState())
        .town(business.getTown())
        .createdAt(business.getCreatedAt().toString())
        .isActive(business.isActive())
        .updatedAt(updatedAt)
        .build();

    return AuthResponse.builder()
        .token(token)
        .user(response)
        .business(businessResponse)
        .build();
  }


  public AuthResponse register(RegisterRequest request) {

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new Exceptions("The user name is already in use.");
    }

    if (!businessRepository.existsByBusinessUuid(request.getBusinessUuid())) {
      throw new Exceptions("The business does not exist");
    }

    Integer rol = request.getRol() != null ? request.getRol() : 3;

    User user = User.builder().username(request.getUsername())
        .name(request.getName())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .username(request.getUsername())
        .rol(rol)
        .businessUuid(request.getBusinessUuid())
        .status(true)
        .password(passwordEncoder.encode(request.getPassword()))
        .fcmToken(request.getFcmToken())
        .build();
    User userSaved;
    userSaved = userRepository.save(user);

    String token = jwtService.getToken(user);

    UserResponse response = UserResponse.builder()
        .id(userSaved.getId())
        .email(userSaved.getEmail())
        .lastname(userSaved.getLastname())
        .name(userSaved.getName())
        .username(userSaved.getUsername())
        .status(userSaved.getStatus())
        .rol(userSaved.getRol())
        .businessUuid(userSaved.getBusinessUuid())
        .build();

    Business business = businessRepository.findByBusinessUuid(user.getBusinessUuid()).orElseThrow();

    String updatedAt = business.getUpdatedAt() != null ? business.getUpdatedAt().toString() : null;

    BusinessResponse businessResponse = BusinessResponse.builder()
        .businessUuid(business.getBusinessUuid())
        .name(business.getName())
        .address(business.getAddress())
        .country(business.getCountry())
        .documentNumber(business.getDocumentNumber())
        .documentType(business.getDocumentType())
        .lenguajeIso2(business.getLenguajeIso2())
        .lenguajeIso3(business.getLenguajeIso3())
        .postalCode(business.getPostalCode())
        .province(business.getProvince())
        .state(business.getState())
        .town(business.getTown())
        .createdAt(business.getCreatedAt().toString())
        .updatedAt(updatedAt)
        .isActive(business.isActive())
        .build();

    return AuthResponse.builder()
        .token(token)
        .user(response)
        .business(businessResponse)
        .build();


  }


  public void updateUser(Integer userId, RegisterRequest request) {

    User user = userRepository.findById(userId).orElseThrow(() -> new Exceptions("User not found"));
    user.setName(request.getName());
    user.setLastname(request.getLastname());
    user.setEmail(request.getEmail());
    user.setRol(request.getRol());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setStatus(request.getStatus());
    if (!businessRepository.existsByBusinessUuid(request.getBusinessUuid())) {
      throw new Exceptions("The business does not exist");
    }
    user.setBusinessUuid(request.getBusinessUuid());
    userRepository.save(user);
  }

  public List<User> getUsersByBusinessUuid(String businessUuid) {
    return userRepository.findByBusinessUuid(businessUuid);
  }

}
