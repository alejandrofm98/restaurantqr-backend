package com.example.demo.features.auth;

import com.example.demo.features.business.BusinessResponse;
import com.example.demo.features.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBusinessOwnerResponse {
  private String token;
  UserResponse user;
  BusinessResponse business;
}
