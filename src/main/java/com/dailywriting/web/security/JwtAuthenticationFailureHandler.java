package com.dailywriting.web.security;

import com.dailywriting.web.global.error.ErrorCode;
import com.dailywriting.web.global.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED_ERROR;
        response.setStatus(errorCode.getStatus());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
        objectMapper.writeValue(bufferedOutputStream, ErrorResponse.create(errorCode));
    }
}
