package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private ResponseData response;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseData {
        private String token;
        Object user;
        Object business;
    }
}

