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

        JwtClaimDto jwtClaimDto = new JwtClaimDto();
        jwtClaimDto.setExpiration(Date.from(LocalDateTime.of(2020, 7, 23, 23, 59, 59).atZone(ZoneId.systemDefault()).toInstant()));
        jwtClaimDto.setUserId(999999);

        String jwtToken = jwtTokenProvider.encode((jwtClaimDto));
        JwtClaimDto decodedJwtClaimDto = jwtTokenProvider.decode(jwtToken);

        assertEquals(decodedJwtClaimDto.getUserId(), jwtClaimDto.getUserId());
        assertEquals(decodedJwtClaimDto.getExpiration().getTime(), jwtClaimDto.getExpiration().getTime());
    }
}
