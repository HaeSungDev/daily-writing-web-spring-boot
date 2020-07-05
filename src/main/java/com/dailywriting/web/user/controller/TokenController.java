package com.dailywriting.web.user.controller;

import com.dailywriting.web.common.CommonExceptionResponseBody;
import com.dailywriting.web.security.JwtPayload;
import com.dailywriting.web.security.JwtTokenProvider;
import com.dailywriting.web.user.exception.LoginFailException;
import com.dailywriting.web.user.domain.User;
import com.dailywriting.web.user.domain.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {
    final private UserService userService;
    final private JwtTokenProvider jwtTokenProvider;

    @Value("${app.jwt.expiration-seconds}")
    private long expirationSeconds;

    @PostMapping()
    public GenerateTokenResponseDto generateToken(@RequestBody GenerateTokenRequestDto generateTokenRequestDto) {
        User loginUser = userService.login(generateTokenRequestDto.getUsername(), generateTokenRequestDto.getPassword());

        Date expiration = Date.from(
                LocalDateTime.now().plus(expirationSeconds, ChronoUnit.SECONDS).atZone(ZoneId.systemDefault()).toInstant()
        );

        JwtPayload jwtPayload = JwtPayload
                .builder()
                .userId(loginUser.getId())
                .expiration(expiration)
                .build();

        String token = jwtTokenProvider.encode(jwtPayload);

        GenerateTokenResponseDto generateTokenResponseDto = new GenerateTokenResponseDto();
        generateTokenResponseDto.setToken(token);

        return generateTokenResponseDto;
    }

    // DTO
    @Data
    public static class GenerateTokenRequestDto {
        String username;
        String password;
    }

    @Data
    public static class GenerateTokenResponseDto {
        String token;
    }

    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> handleDuplicateUser(LoginFailException loginFailException) {
        CommonExceptionResponseBody responseBody = new CommonExceptionResponseBody("LoginFail", "아이디 또는 패스워드가 틀렸습니다.");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
