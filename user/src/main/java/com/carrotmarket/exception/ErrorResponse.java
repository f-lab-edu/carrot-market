package com.carrotmarket.exception;

public record ErrorResponse(
        int status,
        String message
) {
}