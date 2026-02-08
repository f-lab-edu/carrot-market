package com.carrotmarket.exception.handler;

import com.carrotmarket.controller.TokenValidationController;
import com.carrotmarket.controller.dto.response.TokenValidationResponse;
import com.carrotmarket.exception.TokenValidatorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses =  TokenValidationController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenValidatorExceptionHandler {

    @ExceptionHandler(TokenValidatorException.class)
    public ResponseEntity<TokenValidationResponse> handleTokenValidatorException(TokenValidatorException e) {
        log.error("[ERROR] Exception: {}", e.getMessage());

        return ResponseEntity
                .status(e.getCode())
                .body(new TokenValidationResponse(false, e.getMessage()));
    }

}
