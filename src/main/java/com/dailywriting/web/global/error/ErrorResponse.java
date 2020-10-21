package com.dailywriting.web.global.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String code;
    private String message;
    private List<ParameterError> parameterErrors;

    private ErrorResponse(String code, String message, List<ParameterError> parameterErrors) {
        this.code = code;
        this.message = message;
        this.parameterErrors = parameterErrors;
    }

    public static ErrorResponse create(ErrorCode errorCode) {
        return new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            new ArrayList<>()
        );
    }

    public static ErrorResponse create(ErrorCode errorCode, List<FieldError> fieldErrors) {
        List<ParameterError> parameterErrors = fieldErrors
            .stream()
            .map(
                (fieldError) ->
                    ErrorResponse.ParameterError.create(fieldError.getField(), fieldError.getDefaultMessage())
            )
            .collect(Collectors.toList());

        return new ErrorResponse(
            errorCode.getCode(),
            errorCode.getMessage(),
            parameterErrors
        );
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ParameterError {
        String parameterName;
        String message;

        private ParameterError(String parameterName, String message) {
            this.parameterName = parameterName;
            this.message = message;
        }

        @Builder
        public static ParameterError create(String parameterName, String message) {
            return new ParameterError(
                parameterName,
                message
            );
        }
    }
}
