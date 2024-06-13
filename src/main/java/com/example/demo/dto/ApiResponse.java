package com.example.demo.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

  private final Object response;
  private boolean error = false;
  private String errorDescription = "";


}
