package com.dailywriting.web.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class JoinDto {
    @Email(message = "올바른 이메일을 입력해주세요.")
    @NotBlank
    String username;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[*.!@$%^&(){}\\[\\]:;<>,?\\\\/~_+\\-=|]).{8,32}$", message = "패스워드는 영문자, 숫자, 특수문자가 포함 된 8 ~ 32자 입니다.")
    String password;

    @Builder
    public JoinDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
