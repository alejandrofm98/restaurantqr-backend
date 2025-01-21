package com.example.demo.common.config;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;

public class CachedBodyServletInputStreamConfig extends ServletInputStream {
    private final ByteArrayInputStream inputStream;

    public CachedBodyServletInputStreamConfig(byte[] body) {
        this.inputStream = new ByteArrayInputStream(body);
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public boolean isFinished() {
        return inputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }
}
