package com.carrotmarket.exception;

import com.carrotmarket.controller.dto.response.TokenValidationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses =  TokenValidatorExceptionHandler.class)
public class TokenValidatorExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<TokenValidationResponse> handleRuntimeException(
            RuntimeException e,
            HttpServletRequest request
    ) {
        log.error("[ERROR] TID: {} | Exception: {}", request.getHeader("TID"), e.getMessage());

        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new TokenValidationResponse(false, null, "토큰 검증 중 오류가 발생했습니다.")
                );
    }
}
