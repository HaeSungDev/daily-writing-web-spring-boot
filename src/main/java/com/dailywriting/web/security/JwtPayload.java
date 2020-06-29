package com.dailywriting.web.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class JwtPayload {
    long userId;
    Date expiration;

    public boolean isExpired(Date dateForCompare) {
        return this.expiration.before(dateForCompare);
    }
}
