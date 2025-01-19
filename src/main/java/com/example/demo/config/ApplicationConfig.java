package com.example.demo.config;

import static com.example.demo.utils.Constants.CLICK_2_EAT;

import com.example.demo.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableEnversRepositories
@EnableJpaRepositories(basePackages = {
    "com.example.demo"}, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

  private final UserRepository userRepository;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

  }

  @Bean
  FirebaseMessaging firebaseMessaging() throws IOException {
    GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
        new ClassPathResource("firebase-service-account.json").
            getInputStream());

    FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials)
        .build();
    FirebaseApp firebaseApp = null;
    boolean hasBeenInitialized = false;
    List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
    for (FirebaseApp app : firebaseApps) {
      if (app.getName().equals(CLICK_2_EAT)) {
        hasBeenInitialized = true;
        firebaseApp = app;
      }
    }

    if (!hasBeenInitialized) {
      firebaseApp = FirebaseApp.initializeApp(firebaseOptions, CLICK_2_EAT);
    }

    return FirebaseMessaging.getInstance(firebaseApp);

  }

}
