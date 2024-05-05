package com.example.demo.auth;

import static com.example.demo.utils.Constants.CONSTANT_POST;
import static com.example.demo.utils.Constants.CONSTANT_PUBLIC_URL;
import static com.example.demo.utils.Constants.CONSTANT_PUT;
import static com.example.demo.utils.Constants.CONSTANT_ROL_ADMIN;
import static com.example.demo.utils.Constants.CONSTANT_ROL_OWNER;
import static com.example.demo.utils.Constants.CONSTANT_SECURE_URL;

import com.example.demo.config.Log4j2Config;
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
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    AuthResponse authResponse;
    authResponse = authService.register(request);

    String token = authResponse.getResponse().getToken();

    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/register",
            token,
        request.toString()
    );

    return ResponseEntity.ok(authResponse);
  }

  @PutMapping("update/{id}")
  @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
  public ResponseEntity<?> updateUser(@PathVariable Integer id,
      @RequestBody RegisterRequest request) {
    try {
      Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_PUBLIC_URL + "/update/" + id,
          "User updated correctly",
          id + " " + request.toString());
      authService.updateUser(id, request);
      return ResponseEntity.ok(request);
    } catch (Exceptions e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


  @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
  @GetMapping("users")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }


}
