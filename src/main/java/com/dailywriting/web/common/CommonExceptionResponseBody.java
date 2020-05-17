package com.dailywriting.web.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonExceptionResponseBody {
    private String code;
    private String message;
}
