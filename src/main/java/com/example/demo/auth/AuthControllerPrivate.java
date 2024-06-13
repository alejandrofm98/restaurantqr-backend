package com.example.demo.auth;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.Exceptions;
import com.example.demo.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping(CONSTANT_SECURE_URL)
@RequiredArgsConstructor
public class AuthControllerPrivate {


  private final UserRepository userRepository;
  private final AuthService authService;

  @PostMapping(value = "secure")
  public String welcome() {
    return "Welcome from secure endpoint";

  }


  @PostMapping(value = "register")
  @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
  public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
    AuthResponse authResponse;
    authResponse = authService.register(request);

    String token = authResponse.getResponse().getToken();

    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/register",
            token,
        request.toString()
    );
    ApiResponse apiResponse = ApiResponse.builder()
        .response(authResponse)
        .build();
    return ResponseEntity.ok(apiResponse);
  }

  @PutMapping("update/{id}")
  @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
  public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer id,
      @RequestBody RegisterRequest request) {
    try {
      Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_PUBLIC_URL + "/update/" + id,
          "User updated correctly",
          id + " " + request.toString());
      authService.updateUser(id, request);

      ApiResponse apiResponse = ApiResponse.builder()
          .response(request)
          .build();
      return ResponseEntity.ok(apiResponse);
    } catch (Exceptions e) {
      return ResponseEntity.badRequest().body(ApiResponse.builder().build());
    }
  }


  @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
  @GetMapping("users")
  public List<User> getAllUsers() {
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_PUBLIC_URL + "/users",
            "All users displayed correctly",
            userRepository.findAll().toString());
    return userRepository.findAll();

  }


  @GetMapping("/users/{businessUuid}")
  public ResponseEntity<List<User>> getUsersByBusinessUuid(@PathVariable String businessUuid) {
    List<User> users = authService.getUsersByBusinessUuid(businessUuid);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_PUBLIC_URL + "/users/{businessUuid}",
            "Users for business displayed correctly",
            users.toString());
    return ResponseEntity.ok(users);
  }


}
