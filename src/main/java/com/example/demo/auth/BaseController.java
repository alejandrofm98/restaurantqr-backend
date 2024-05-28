package com.example.demo.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public abstract class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected <T> ResponseEntity<ApiResponse<T>> response(T data, HttpServletRequest request, HttpStatus status, long initTime, Boolean hasError, String errorDescription) {
        String requestBody = getRequestBody(request);
        ResponseEntity<ApiResponse<T>> response = new ResponseEntity<>(new ApiResponse<>(data, hasError, errorDescription), status);
        long endTime = System.currentTimeMillis();
        logRequestInfo(request, requestBody, response.toString(), endTime - initTime);
        return response;
    }

    private String getRequestBody(HttpServletRequest request) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            return "{}";
        }
    }

    private void logRequestInfo(HttpServletRequest request, String requestBody, String responseBody, long responseTime) {
        String origin = getOrigin(request);
        String requestUrl = request.getRequestURI();
        String requestMethod = request.getMethod();
        String responseBodyString;
        try {
            responseBodyString = objectMapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException e) {
            responseBodyString = responseBody;
        }

        String logMessage = "[Origin]" + origin + "[/Origin]"
                + "[Request]" + requestUrl + "[/Request]"
                + "[Method]" + requestMethod + "[/Method]"
                + "[Error]" + false + "[/Error]"
                + "[ErrorDescription][/ErrorDescription]"
                + "[RequestBody]" + requestBody + "[/RequestBody]"
                + "[RequestResponse]" + responseBodyString + "[/RequestResponse]"
                + "[Time]" + responseTime + "ms[/Time]";
        logger.info(logMessage);
    }

    private String getOrigin(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null) {
            return xForwardedFor;
        } else {
            return request.getRemoteAddr() + ":" + request.getRemotePort();
        }
    }

}
