package com.carrotmarket.controller.dto.response;

public record TokenValidationResponse(
        boolean valid,
        String userId,
        String errorMessage
) {}
