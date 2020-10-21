package com.dailywriting.web.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> validationExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        return new ResponseEntity<>(ErrorResponse.create(errorCode, fieldErrors), HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> serverExceptionHandler(Exception e) {
        log.debug("CommonExceptionHandler catch exception", e);

        ErrorCode errorCode = ErrorCode.SERVER_ERROR;

        return new ResponseEntity<>(ErrorResponse.create(errorCode), HttpStatus.valueOf(errorCode.getStatus()));
    }
}
