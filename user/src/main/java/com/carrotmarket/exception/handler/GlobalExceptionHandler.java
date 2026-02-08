package com.carrotmarket.exception.handler;

import com.carrotmarket.controller.dto.response.ErrorResponse;
import com.carrotmarket.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("[ERROR] Exception: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleICustomException(CustomException e) {
        log.error("[ERROR] Exception: {}", e.getMessage());

        ErrorResponse response = new ErrorResponse(
                e.getCode(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getCode())
                .body(response);
    }

}