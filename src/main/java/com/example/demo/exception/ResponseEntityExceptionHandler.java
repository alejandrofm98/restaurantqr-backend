package com.example.demo.exception;

import com.example.demo.dto.ApiResponse;
import java.nio.file.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {

    ApiResponse apiResponse = ApiResponse.builder()
        .error(true)
        .errorDescription(ex.getMessage())
        .build();

    return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(value = AccessDeniedException.class)
  public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
    ApiResponse apiResponse = ApiResponse.builder()
        .error(true)
        .errorDescription("Access denied "+ex.getMessage())
        .build();
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }
}
