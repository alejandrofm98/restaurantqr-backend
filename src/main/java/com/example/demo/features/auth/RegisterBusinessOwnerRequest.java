package com.example.demo.features.auth;

import com.example.demo.features.business.BusinessRequest;
import com.example.demo.features.user.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBusinessOwnerRequest {
    UserRequest userRequest;
    BusinessRequest businessRequest;
}
