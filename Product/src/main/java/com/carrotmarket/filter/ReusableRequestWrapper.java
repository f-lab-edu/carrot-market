package com.carrotmarket.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReusableRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] requestBody;

    public ReusableRequestWrapper(HttpServletRequest request, byte[] requestBody) {
        super(request);
        this.requestBody = requestBody;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            private final InputStream inputStream = new ByteArrayInputStream(requestBody);

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
    }

    public byte[] getRequestBody() {
        return requestBody;
    }

}