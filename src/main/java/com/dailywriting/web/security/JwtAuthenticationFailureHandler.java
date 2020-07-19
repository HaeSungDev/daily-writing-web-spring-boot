package com.dailywriting.web.security;

import com.dailywriting.web.common.CommonExceptionCode;
import com.dailywriting.web.common.CommonExceptionResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        CommonExceptionResponseBody responseBody = CommonExceptionResponseBody.create(CommonExceptionCode.UNAUTHORIZED_ERROR);

        response.setStatus(responseBody.getStatus());
        response.setContentType("application/json;charset=utf-8");

        BufferedWriter bufferedWriter = new BufferedWriter(response.getWriter());
        objectMapper.writeValue(bufferedWriter, responseBody);
    }
}
