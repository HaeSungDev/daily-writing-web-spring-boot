package com.dailywriting.web.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> handler(Exception e) {
        log.debug("CommonExceptionHandler catch exception", e);
        return new ResponseEntity<>(new CommonExceptionResponseBody("InternalServerError", "서버 처리 도중 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
