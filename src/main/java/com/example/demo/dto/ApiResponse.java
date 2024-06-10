package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiResponse {

  private final Object response;
  private boolean error;
  private String errorDescription;

  public void setError(){
    this.error = false;
  }

}
