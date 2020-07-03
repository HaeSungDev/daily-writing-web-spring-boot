package com.dailywriting.web.user;

import com.dailywriting.web.common.CommonExceptionResponseBody;
import lombok.Data;
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
    public JoinResponseDto join(@RequestBody JoinRequestDto joinRequestDto) {
        User user = User.builder()
            .username(joinRequestDto.username)
            .password(joinRequestDto.password)
            .build();
        JoinResponseDto joinResponseDto = new JoinResponseDto();
        try {
            joinResponseDto.setUserId(userService.join(user));
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateException();
        }
        return joinResponseDto;
    }

    @Data
    public static class JoinRequestDto {
        String username;
        String password;
    }

    @Data
    public static class JoinResponseDto {
        long userId;
    }

    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponseBody> handleDuplicateUser(UserDuplicateException userDuplicateException) {
        CommonExceptionResponseBody responseBody = new CommonExceptionResponseBody("UserDuplicateError", "이미 존재하는 유저입니다.");
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
