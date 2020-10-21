package com.dailywriting.web.user.controller;

import com.dailywriting.web.user.domain.UserService;
import com.dailywriting.web.user.dto.JoinRequestDto;
import com.dailywriting.web.user.dto.JoinResponseDto;
import com.dailywriting.web.user.exception.UserDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping
    public JoinResponseDto join(@Valid @RequestBody JoinRequestDto joinRequestDto) {
        try {
            return JoinResponseDto
                .builder()
                .userId(userService.join(joinRequestDto))
                .build();
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateException();
        }
    }
}
