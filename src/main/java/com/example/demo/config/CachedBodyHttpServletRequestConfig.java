package com.example.demo.config;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CachedBodyHttpServletRequestConfig extends HttpServletRequestWrapper {
    private final String body;

    public CachedBodyHttpServletRequestConfig(HttpServletRequest request, String body) {
        super(request);
        this.body = body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStreamConfig(body.getBytes());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}

