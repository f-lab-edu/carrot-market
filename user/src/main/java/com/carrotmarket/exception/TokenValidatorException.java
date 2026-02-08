package com.carrotmarket.exception;

public class TokenValidatorException extends CustomException {
    public TokenValidatorException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
