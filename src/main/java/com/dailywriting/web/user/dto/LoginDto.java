package com.dailywriting.web.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;

    @Builder
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
