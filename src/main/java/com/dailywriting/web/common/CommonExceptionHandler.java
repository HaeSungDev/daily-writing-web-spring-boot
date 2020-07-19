package com.dailywriting.web.common;

import com.dailywriting.web.user.exception.UserDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> userDuplicateExceptionHandler(UserDuplicateException userDuplicateException) {
        CommonExceptionResponseBody responseBody = CommonExceptionResponseBody.create(CommonExceptionCode.USER_DUPLICATE_ERROR);
        return new ResponseEntity<>(responseBody, HttpStatus.valueOf(responseBody.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> validationExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        CommonExceptionResponseBody responseBody = CommonExceptionResponseBody
            .create(
                CommonExceptionCode.VALIDATION_ERROR,
                fieldErrors
                    .stream()
                    .map(
                        (fieldError) -> CommonExceptionResponseBody.ParameterError
                            .create(
                                fieldError.getField(), fieldError.getDefaultMessage()
                            )
                    )
                    .collect(Collectors.toList())
            );

        return new ResponseEntity<>(responseBody, HttpStatus.valueOf(responseBody.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> serverExceptionHandler(Exception e) {
        log.debug("CommonExceptionHandler catch exception", e);

        CommonExceptionResponseBody responseBody = CommonExceptionResponseBody.create(
            CommonExceptionCode.SERVER_ERROR
        );

        return new ResponseEntity<>(responseBody, HttpStatus.valueOf(responseBody.getStatus()));
    }
}
