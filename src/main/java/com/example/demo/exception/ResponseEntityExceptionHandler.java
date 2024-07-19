package com.example.demo.exception;

import com.example.demo.dto.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

  @ExceptionHandler(value = ExpiredJwtException.class)
  public ResponseEntity<ApiResponse> handleExpiredJwtException(ExpiredJwtException ex,
      WebRequest request) {
    ApiResponse apiResponse = ApiResponse.builder()
        .error(true)
        .errorDescription("Access denied " + ex.getMessage())
        .build();
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }


  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex,
      WebRequest request) {

    ApiResponse apiResponse = ApiResponse.builder()
        .error(true)
        .errorDescription(ex.getMessage())
        .build();

    return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }


}
