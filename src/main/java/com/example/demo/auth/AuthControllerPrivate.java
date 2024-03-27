package com.example.demo.auth;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthControllerPrivate {

    @Autowired
    private UserRepository userRepository;

        @PostMapping(value = "secure")
        public String welcome(){
            return "Welcome from secure endpoint";

    }

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
