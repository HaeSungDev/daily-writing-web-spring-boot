package com.dailywriting.web.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @PostMapping("/join")
    public long join(@RequestBody JoinDto joinDto) {
        return userService.join(joinDto.username, joinDto.password);
    }

    @Data
    public static class JoinDto {
        String username;
        String password;
    }
}
