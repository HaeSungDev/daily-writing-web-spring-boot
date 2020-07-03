package com.dailywriting.web.security;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class JwtPayload {
    long userId;
    Date expiration;

    public boolean isExpired(Date dateForCompare) {
        return this.expiration.before(dateForCompare);
    }
}
