package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    String name;
    String lastname;
    String email;
    String username;
    String password;
    String role;
    String businessUuid;
    String fcmToken;
    private Boolean status;

}
