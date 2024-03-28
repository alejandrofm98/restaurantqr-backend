package com.example.demo.auth;

import com.example.demo.config.Log4j2Config;
import com.example.demo.entity.User;
import com.example.demo.exception.Exceptions;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping(CONSTANT_SECURE_URL)
@RequiredArgsConstructor
public class AuthControllerPrivate {


    private final UserRepository userRepository;
    private final AuthService authService;

        @PostMapping(value = "secure")
        public String welcome(){
            return "Welcome from secure endpoint";

    }


    @PostMapping(value ="register")
    @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse;
        authResponse = authService.register(request);
        Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_PUBLIC_URL + "/register",
                authResponse.getToken(),
                request.toString()
        );
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody RegisterRequest request) {
        try {
            Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_PUBLIC_URL + "/update/"+id,
                    "User updated correctly",
                    id+" "+request.toString());
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
