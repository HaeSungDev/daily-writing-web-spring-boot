package com.dailywriting.web.user.controller;

import com.dailywriting.web.common.CommonExceptionResponseBody;
import com.dailywriting.web.user.domain.UserService;
import com.dailywriting.web.user.dto.JoinDto;
import com.dailywriting.web.user.dto.JoinResponseDto;
import com.dailywriting.web.user.exception.UserDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping
    public JoinResponseDto join(@RequestBody JoinDto joinDto) {
        try {
            return JoinResponseDto
                .builder()
                .userId(userService.join(joinDto))
                .build();
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateException();
        }
    }

    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> handleDuplicateUser(UserDuplicateException userDuplicateException) {
        CommonExceptionResponseBody responseBody = new CommonExceptionResponseBody("UserDuplicateError", "이미 존재하는 유저입니다.");
        return new ResponseEntity<>(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
