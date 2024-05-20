package com.example.demo;

import com.example.demo.config.AuditorAwareImpl;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;


@SpringBootApplication
public class DemoApplication {


  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}
