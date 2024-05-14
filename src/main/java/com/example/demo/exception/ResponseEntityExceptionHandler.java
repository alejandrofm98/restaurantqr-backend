package com.example.demo.exception;

import com.example.demo.dto.ErrorDTO;
import java.nio.file.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseEntityExceptionHandler {
  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException ex, WebRequest request) {
    ErrorDTO errorDTO = ErrorDTO.builder().error(true)
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(value = AccessDeniedException.class)
  public ResponseEntity<ErrorDTO> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
    ErrorDTO errorDTO = ErrorDTO.builder().error(true)
        .message("Access denied "+ex.getMessage())
        .build();
    return new ResponseEntity<>(errorDTO, HttpStatus.FORBIDDEN);
  }
}
