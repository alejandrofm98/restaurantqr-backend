package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class AuthControllerPrivate {
    @RestController
    @RequestMapping("/api/v1")
    @RequiredArgsConstructor
    public class AuthController {
        @PostMapping(value = "secure")
        public String welcome(){
            return "Welcome from secure endpoint";
        }
    }
}
