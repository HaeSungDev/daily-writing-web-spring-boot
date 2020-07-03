package com.dailywriting.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt.secret-key}")
    private String jwtSecretKey;

    public String encode(JwtPayload jwtPayload) {
        return Jwts
                .builder()
                .setExpiration(jwtPayload.getExpiration())
                .claim("user_id", jwtPayload.getUserId())
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtPayload decode(String jwtToken) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(jwtSecretKey.getBytes())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        JwtPayload jwtPayload = JwtPayload
                .builder()
                .userId(claims.get("user_id", Long.class))
                .expiration((Date) claims.getExpiration())
                .build();

        return jwtPayload;
    }
}
