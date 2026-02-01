package com.carrotmarket.controller.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenValidationRequest(
        @NotBlank String token
) {}
