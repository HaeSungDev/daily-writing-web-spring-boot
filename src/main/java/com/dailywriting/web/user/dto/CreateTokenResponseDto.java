package com.dailywriting.web.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateTokenResponseDto {
    String token;

    @Builder
    public CreateTokenResponseDto(String token) {
        this.token = token;
    }
}
