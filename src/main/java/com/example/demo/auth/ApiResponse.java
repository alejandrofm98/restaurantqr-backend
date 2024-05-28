package com.example.demo.auth;

public class ApiResponse<T> {
    private boolean error;
    private String errorDescription;
    private T response;

    public ApiResponse(T response, boolean hasError, String errorDescription) {
        this.error = hasError;
        this.errorDescription = errorDescription;
        this.response = response;
    }

    public ApiResponse(String errorMessage) {
        this.error = true;
        this.errorDescription = errorMessage;
    }

    public boolean isError() {
        return error;
    }

    public T getResponse() {
        return response;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
