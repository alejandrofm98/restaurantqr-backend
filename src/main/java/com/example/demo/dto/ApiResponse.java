package com.example.demo.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

  private final Object response;
  @Builder.Default
  private boolean error = false;
  @Builder.Default
  private String errorDescription = "";


}
