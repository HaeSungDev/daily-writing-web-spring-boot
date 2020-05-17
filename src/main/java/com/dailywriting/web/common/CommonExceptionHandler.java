package com.dailywriting.web.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> handler(Exception e) {
        // TODO: Error Logging And Notify LIKE Sentry
        return new ResponseEntity<>(new CommonExceptionResponseBody("InternalServerError", "서버 처리 도중 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
