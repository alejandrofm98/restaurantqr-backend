package com.example.demo.features.user;

import static com.example.demo.common.utils.Constants.CONSTANT_GET;
import static com.example.demo.common.utils.Constants.CONSTANT_PUBLIC_URL;
import static com.example.demo.common.utils.Constants.CONSTANT_PUT;
import static com.example.demo.common.utils.Constants.CONSTANT_ROL_ADMIN;
import static com.example.demo.common.utils.Constants.CONSTANT_ROL_OWNER;
import static com.example.demo.common.utils.Constants.CONSTANT_SECURE_URL;

import com.example.demo.common.config.Log4j2Config;
import com.example.demo.common.dto.ApiResponse;
import com.example.demo.common.exception.Exceptions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CONSTANT_SECURE_URL)
@PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("users")
  public ResponseEntity<ApiResponse> findAllUsers() {
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


  @PutMapping("user/{id}")
  public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer id,
      @RequestBody UserRequest request) {
    try {
      Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_PUBLIC_URL + "/update/" + id,
          "User updated correctly",
          id + " " + request.toString());
      userService.updateUser(id, request);

      ApiResponse apiResponse = ApiResponse.builder()
          .response(request)
          .build();
      return ResponseEntity.ok(apiResponse);
    } catch (Exceptions e) {
      return ResponseEntity.badRequest().body(ApiResponse.builder().build());
    }
  }

}
