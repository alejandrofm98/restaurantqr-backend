package com.example.demo.auth;

import com.example.demo.exceptions.Exceptions;
import com.example.demo.entity.Usuario_rol;
import com.example.demo.jwt.JwtService;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.Usuario_rolRepository;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final Usuario_rolRepository usuario_rolRepository;
    private final RolRepository rolRepository;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exceptions("El nombre de usuario ya está en uso.");
        }
        User user = User.builder().username(request.getUsername())
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();


        userRepository.save(user);

        Usuario_rol rol = Usuario_rol.builder().id_usuario(user.getId()).id_rol(request.getRol()).build();

        usuario_rolRepository.save(rol);

        return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }

}
