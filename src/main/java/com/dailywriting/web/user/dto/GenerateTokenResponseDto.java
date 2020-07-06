package com.dailywriting.web.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenerateTokenResponseDto {
    String token;

    @Builder
    public GenerateTokenResponseDto(String token) {
        this.token = token;
    }
}
