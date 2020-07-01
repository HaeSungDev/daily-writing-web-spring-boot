package com.dailywriting.web.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtTokenProviderUnitTest {
    @Test
    public void JwtEncodeDecodeTest() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecretKey", "tKCVbZ6wvApzaq649PdpbGV9yopAZkOY4MZmAv_lK90", String.class);

        Date expiration = Date.from(LocalDateTime.of(2020, 7, 23, 23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        JwtPayload jwtClaimDto = new JwtPayload(999999, expiration);

        String jwtToken = jwtTokenProvider.encode((jwtClaimDto));
        JwtPayload decodedJwtClaimDto = jwtTokenProvider.decode(jwtToken);

        assertEquals(decodedJwtClaimDto.getUserId(), jwtClaimDto.getUserId());
        assertEquals(decodedJwtClaimDto.getExpiration().getTime(), jwtClaimDto.getExpiration().getTime());
    }
}
