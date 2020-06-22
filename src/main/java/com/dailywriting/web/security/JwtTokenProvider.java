package com.dailywriting.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtTokenProvider {
    @Value("${app.jwt.secret-key}")
    private String jwtSecretKey;

    public String encode(JwtClaimDto jwtClaimDto) {
        return Jwts
                .builder()
                .setExpiration(jwtClaimDto.getExpiration())
                .claim("user_id", jwtClaimDto.getUserId())
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtClaimDto decode(String jwtToken) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(jwtSecretKey.getBytes())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        JwtClaimDto jwtClaimDto = new JwtClaimDto();
        jwtClaimDto.setUserId(claims.get("user_id", Long.class));
        jwtClaimDto.setExpiration((Date)claims.getExpiration());

        return jwtClaimDto;
    }
}
