package com.carrotmarket.controller.dto.response;

public record LoginResponseDto(
        String userId,
        String accessToken,
        String refreshToken
) {
}