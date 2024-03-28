package com.example.demo.auth;

import com.example.demo.entity.User;
import com.example.demo.exception.Exceptions;
import com.example.demo.jwt.JwtService;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        User idUser = searchUserWithIdByUserName(user.getUsername());

        return new AuthResponse(token, idUser.getRol());
    }

    public User searchUserWithIdByUserName(String userName) {
        return userRepository.findUserWithIdByUsername(userName);
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exceptions("The user name is already in use.");
        }
        User user = User.builder().username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .rol(request.getRol())
                .business_uuid(request.getBusiness_uuid())
                .status(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        String token=jwtService.getToken(user);
        return new AuthResponse(token, user.getRol());
    }


    public void updateUser(Integer userId, RegisterRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exceptions("User not found"));
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setRol(request.getRol());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(request.getStatus());
        user.setBusiness_uuid(request.getBusiness_uuid());
        userRepository.save(user);
    }

}
