package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthControllerPrivate {

        @PostMapping(value = "secure")
        public String welcome(){
            return "Welcome from secure endpoint";

    }
}
