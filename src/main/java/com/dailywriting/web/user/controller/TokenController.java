package com.dailywriting.web.user.controller;

import com.dailywriting.web.common.CommonExceptionResponseBody;
import com.dailywriting.web.security.JwtPayload;
import com.dailywriting.web.security.JwtTokenProvider;
import com.dailywriting.web.user.domain.User;
import com.dailywriting.web.user.domain.UserService;
import com.dailywriting.web.user.dto.CreateTokenResponseDto;
import com.dailywriting.web.user.dto.CreateTokenRequestDto;
import com.dailywriting.web.user.exception.LoginFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public CreateTokenResponseDto createToken(@Valid @RequestBody CreateTokenRequestDto createTokenRequestDto) {
        User loginUser = userService.login(createTokenRequestDto);

        Date expiration = Date.from(
                LocalDateTime.now().plus(expirationSeconds, ChronoUnit.SECONDS).atZone(ZoneId.systemDefault()).toInstant()
        );

        JwtPayload jwtPayload = JwtPayload
                .builder()
                .userId(loginUser.getId())
                .expiration(expiration)
                .build();

        String token = jwtTokenProvider.encode(jwtPayload);

        return CreateTokenResponseDto
            .builder()
            .token(token)
            .build();
    }
}
