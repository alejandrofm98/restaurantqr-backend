package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String name;
    String lastname;
    String email;
    String username;
    String password;
    Integer rol;
    String businessUuid;
    Boolean status;
    String fcmToken;
}
