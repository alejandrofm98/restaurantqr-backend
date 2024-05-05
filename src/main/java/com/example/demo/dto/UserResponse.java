package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    Integer id;
    String email;
    String lastname;
    String name;
    String username;
    Boolean status;
    Integer rol;
    String businessUuid;
}
