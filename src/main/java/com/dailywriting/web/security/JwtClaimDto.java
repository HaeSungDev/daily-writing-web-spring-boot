package com.dailywriting.web.security;

import lombok.Data;

import java.util.Date;

@Data
public class JwtClaimDto {
    Date expiration;
    long userId;
}
