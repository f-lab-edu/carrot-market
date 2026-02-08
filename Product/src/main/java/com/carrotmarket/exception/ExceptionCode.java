package com.carrotmarket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    PRODUCT_NOT_FOUND(400, "상품을 찾을 수 없습니다.");

    private final int code;
    private  final String message;

}
