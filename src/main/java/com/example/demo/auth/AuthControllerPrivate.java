package com.example.demo.auth;

import static com.example.demo.utils.Constants.CONSTANT_GET;
import static com.example.demo.utils.Constants.CONSTANT_POST;
import static com.example.demo.utils.Constants.CONSTANT_PUBLIC_URL;
import static com.example.demo.utils.Constants.CONSTANT_PUT;
import static com.example.demo.utils.Constants.CONSTANT_ROL_ADMIN;
import static com.example.demo.utils.Constants.CONSTANT_ROL_OWNER;
import static com.example.demo.utils.Constants.CONSTANT_SECURE_URL;
import static com.example.demo.utils.Constants.EMAIL_REGISTER_TEMPLATE;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.dto.response.EmailDetails;
import com.example.demo.entity.User;
import com.example.demo.exception.Exceptions;
import com.example.demo.services.EmailService;
import com.example.demo.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


  private final UserService userService;
  private final AuthService authService;
  private final EmailService emailService;

  @PostMapping(value = "register")
  @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
  public ResponseEntity<ApiResponse> register(@RequestBody UserRequest request) {
    AuthResponse authResponse;
    authResponse = authService.register(request);

    String token = authResponse.getToken();
    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/register",
        token,
        request.toString()
    );

    // Lee el contenido del archivo HTML
    try {
      String htmlContent = new String(
          Objects.requireNonNull(getClass().getResourceAsStream(EMAIL_REGISTER_TEMPLATE))
              .readAllBytes());
      EmailDetails emailDetails = new EmailDetails(request.getEmail(),
          "Registro realizado correctamente", htmlContent, "");
      emailService.sendMail(emailDetails);

    } catch (IOException e) {
      Log4j2Config.logRequestError("Error finding registration template for email");
    }

    ApiResponse apiResponse = ApiResponse.builder()
        .response(authResponse)
        .build();
    return ResponseEntity.ok(apiResponse);

  }

  @PutMapping("update/{id}")
  @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
  public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer id,
      @RequestBody UserRequest request) {
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
  public ResponseEntity<ApiResponse> fetchAllUsers() {
    try {
      List<User> users = userService.findAllUsers();
      Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_PUBLIC_URL + "/users",
          "All users displayed correctly",
          users.toString());

      ApiResponse apiResponse = ApiResponse.builder()
          .response(users)
          .build();
      return ResponseEntity.ok(apiResponse);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ApiResponse.builder().build());
    }
  }

  @GetMapping("/users/{businessUuid}")
  public ResponseEntity<ApiResponse> getUsersByBusinessUuid(@PathVariable String businessUuid) {
    try {
      List<User> users = userService.findUsersByBussinesUuid(businessUuid);
      Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_PUBLIC_URL + "/users/" + businessUuid,
          "Users for business displayed correctly",
          users.toString());

      if (users.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.builder()
                .error(true)
                .errorDescription("No users found for the given business UUID")
                .build());
      }

      ApiResponse apiResponse = ApiResponse.builder()
          .response(users)
          .build();
      return ResponseEntity.ok(apiResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.builder()
              .error(true)
              .errorDescription("An error occurred while fetching users")
              .build());
    }


  }
}

