package com.example.demo.config;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@NoArgsConstructor
public class ErrorConfig {
   boolean error;
   String description;
    public ErrorConfig(boolean error,String description) {
        this.error = error;
        this.description = description;
    }

    public ErrorConfig(boolean error) {
        this.error = error;
    }
}
