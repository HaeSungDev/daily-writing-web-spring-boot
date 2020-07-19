package com.dailywriting.web.common;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonExceptionResponseBody {
    private int status;
    private String code;
    private String message;
    private List<ParameterError> parameterErrors;

    private CommonExceptionResponseBody(int status, String code, String message, List<ParameterError> parameterErrors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.parameterErrors = parameterErrors;
    }

    public static CommonExceptionResponseBody create(CommonExceptionCode commonExceptionCode) {
        return new CommonExceptionResponseBody(
            commonExceptionCode.getStatus(),
            commonExceptionCode.getCode(),
            commonExceptionCode.getMessage(),
            new ArrayList<>()
        );
    }

    public static CommonExceptionResponseBody create(CommonExceptionCode commonExceptionCode, List<ParameterError> parameterErrors) {
        return new CommonExceptionResponseBody(
            commonExceptionCode.getStatus(),
            commonExceptionCode.getCode(),
            commonExceptionCode.getMessage(),
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
