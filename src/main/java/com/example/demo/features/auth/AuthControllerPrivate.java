package com.example.demo.features.auth;

import static com.example.demo.common.utils.Constants.CONSTANT_POST;
import static com.example.demo.common.utils.Constants.CONSTANT_PUBLIC_URL;
import static com.example.demo.common.utils.Constants.CONSTANT_ROL_ADMIN;
import static com.example.demo.common.utils.Constants.CONSTANT_ROL_OWNER;
import static com.example.demo.common.utils.Constants.CONSTANT_SECURE_URL;
import static com.example.demo.common.utils.Constants.EMAIL_REGISTER_TEMPLATE;

import com.example.demo.common.config.Log4j2Config;
import com.example.demo.features.user.UserRequest;
import com.example.demo.common.dto.ApiResponse;
import com.example.demo.features.email.EmailDetails;
import com.example.demo.features.email.EmailService;
import com.example.demo.features.user.UserService;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
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


}

