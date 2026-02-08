package com.carrotmarket.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final int code;

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
    }

}
