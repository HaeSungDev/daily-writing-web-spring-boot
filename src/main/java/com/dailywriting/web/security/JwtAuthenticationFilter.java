package com.dailywriting.web.security;

import com.dailywriting.web.common.CommonUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    final JwtTokenProvider jwtTokenProvider;
    final JwtAuthenticationFailureHandler failureHandler;

    final String JWT_TOKEN_HEADER = "Authorization";
    final String JWT_TOEKN_PREFIX = "Bearer ";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<HttpServletRequest> optionalHttpServletRequest = CommonUtils.safeCast(request, HttpServletRequest.class);
        Optional<HttpServletResponse> optionalHttpServletResponse = CommonUtils.safeCast(request, HttpServletResponse.class);

        if (optionalHttpServletRequest.isPresent() && optionalHttpServletResponse.isPresent()) {
            HttpServletRequest httpServletRequest = optionalHttpServletRequest.get();
            HttpServletResponse httpServletResponse = optionalHttpServletResponse.get();

            String tokenHeader = httpServletRequest.getHeader(JWT_TOKEN_HEADER);
            if (tokenHeader != null && tokenHeader.startsWith(JWT_TOEKN_PREFIX)) {
                String jwtToken = tokenHeader.substring(JWT_TOEKN_PREFIX.length());

                JwtPayload jwtPayload;
                try {
                    jwtPayload = jwtTokenProvider.decode(jwtToken);
                } catch (JwtException e) {
                    failureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, new JwtAuthenticationException("token is invalid"));
                    return;
                }

                if (jwtPayload.isExpired(new Date())) {
                    failureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, new JwtAuthenticationException("token is expired"));
                }

                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(jwtPayload, jwtToken));
            }
        }
        chain.doFilter(request, response);
    }
}
