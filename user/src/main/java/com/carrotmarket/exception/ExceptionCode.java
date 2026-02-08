package com.carrotmarket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    EMAIL_ALREADY_EXISTS(409, "이미 사용중인 이메일입니다."),
    NICKNAME_ALREADY_EXISTS(409, "이미 사용중인 닉네임입니다."),

    USER_NOT_FOUND(400, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(401, "비밀번호가 올바르지 않습니다."),

    INVALID_ACCESS_TOKEN(401, "유효하지 않은 access token입니다."),
    INVALID_REFRESH_TOKEN(401, "유효하지 않은 refresh token입니다."),
    NOT_MATCH_REFRESH_TOKEN(401, "일치하지 않는 refresh token입니다."),
    BLACKLISTED_TOKEN(401, "블랙리스트된 토큰입니다.");

    private final int code;
    private  final String message;

}
