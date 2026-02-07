package com.carrotmarket.controller.dto.response;

public record ErrorResponse(
        int status,
        String message
) {
}