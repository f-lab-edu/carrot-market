package com.carrotmarket.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenValidationResponse(
        boolean valid,
        @JsonProperty String errorMessage
) {
    public TokenValidationResponse(boolean valid) {
        this(valid, null);
    }
}