package com.carrotmarket.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private static final String TID = "TID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        byte[] requestBody = request.getInputStream().readAllBytes();

        ReusableRequestWrapper loggingRequestWrapper = new ReusableRequestWrapper(request, requestBody);
        ReusableRequestWrapper processingRequestWrapper = new ReusableRequestWrapper(request, requestBody);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        String tid = loggingRequestWrapper.getHeader(TID);
        if (tid == null || tid.isEmpty()) {
            tid = UUID.randomUUID().toString();
        }
        MDC.put(TID, tid);
        responseWrapper.addHeader(TID, tid);

        try {
            logRequest(loggingRequestWrapper);

            filterChain.doFilter(processingRequestWrapper, responseWrapper);
        } finally {
            logResponse(responseWrapper);
            MDC.clear();
        }
    }

    private void logRequest(ReusableRequestWrapper request) throws IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        byte[] requestBodyBytes = request.getRequestBody();
        String body = requestBodyBytes.length == 0 ? "{}" : objectMapper.writeValueAsString(
                objectMapper.readValue(requestBodyBytes, Object.class)
        );

        log.info("[REQUEST] {} {} | Body: {}", method, uri, body);
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        int status = response.getStatus();
        String body = new String(response.getContentAsByteArray());

        log.info("[RESPONSE] Status: {} | Body: {}", status, body);
        response.copyBodyToResponse();
    }

}