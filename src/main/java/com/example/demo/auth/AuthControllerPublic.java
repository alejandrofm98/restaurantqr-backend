package com.example.demo.auth;

import com.example.demo.config.ErrorConfig;
import com.example.demo.config.FilterConfig;
import com.example.demo.config.Log4j2Config;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthControllerPublic {

    private final AuthService authService;

    @PostMapping(value ="login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
            AuthResponse authResponse = authService.login(request);
            Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_LOGIN_URL + "/login",
                    authResponse.getToken(),
                    request.toString()
            );
            return ResponseEntity.ok(authResponse);
    }

    @PostMapping(value ="register")
        public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse;
            authResponse = authService.register(request);
            Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_LOGIN_URL + "/register",
                    authResponse.getToken(),
                    request.toString()
            );
            return ResponseEntity.ok(authResponse);
    }






}



