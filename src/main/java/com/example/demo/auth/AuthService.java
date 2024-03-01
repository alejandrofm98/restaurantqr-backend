package com.example.demo.auth;

import com.example.demo.jwt.JwtService;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder().username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .rol(Role.USER)
                .build();

       userRepository.save(user);

       return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }

}
