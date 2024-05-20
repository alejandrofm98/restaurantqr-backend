package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
  private boolean error;
  private String message;
}
