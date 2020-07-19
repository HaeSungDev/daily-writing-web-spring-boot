package com.dailywriting.web.common;

import lombok.Getter;

@Getter
public enum CommonExceptionCode {
    VALIDATION_ERROR(400,"ValidationError", "올바른 파라미터를 입력해주세요."),
    SERVER_ERROR(500, "InternalServerError", "서버 처리 도중 오류가 발생했습니다."),

    USER_DUPLICATE_ERROR(422, "UserDuplicateError", "이미 존재하는 유저입니다.");

    private int status;
    private String code;
    private String message;

    CommonExceptionCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
