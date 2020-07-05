package com.dailywriting.web.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinResponseDto {
    long userId;

    @Builder
    public JoinResponseDto(long userId) {
        this.userId = userId;
    }
}
