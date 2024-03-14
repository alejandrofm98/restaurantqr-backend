package com.example.demo.auth;

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

    private final FilterConfig filter = new FilterConfig();

    @PostMapping(value ="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        try {
            AuthResponse authResponse = authService.login(request);
            Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_URL + CONSTANT_LOGIN_URL + "/login", CONSTANT_URL,
                    authResponse.getToken(),
                    request.toString()
            );
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            Log4j2Config.logRequestError("Se ha producido un error en el inicio de sesión: "+e.getMessage());
            // Devolución de respuesta JSON genérica
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse(true));
        }

    }

    @PostMapping(value ="register")
        public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse authResponse;
        try {
            authResponse = authService.register(request);
            Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_URL + CONSTANT_LOGIN_URL + "/register", CONSTANT_URL,
                    authResponse.getToken(),
                    request.toString()
            );
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            Log4j2Config.logRequestError(e.getMessage());
            // Devolución de respuesta JSON genérica
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthResponse(true));
        }

    }






}



