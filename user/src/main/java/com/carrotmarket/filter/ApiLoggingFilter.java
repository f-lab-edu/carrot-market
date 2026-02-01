package com.carrotmarket.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final int CACHE_LIMIT = 10 * 1024 * 1024; // 10MB

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request, CACHE_LIMIT);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            logRequest(requestWrapper);
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logResponse(responseWrapper);
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String tid = request.getHeader("TID");
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String body = new String(request.getContentAsByteArray());

        log.info("[REQUEST] TID: {} | {} {} | Body: {}", tid, method, uri, body);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String tid = response.getHeader("TID");
        int status = response.getStatus();
        String body = new String(response.getContentAsByteArray());

        log.info("[RESPONSE] TID: {} | Status: {} | Body: {}", tid, status, body);
    }

}