package com.dopamingu.be.domain.global.security;

import com.dopamingu.be.domain.auth.application.JwtTokenService;
import com.dopamingu.be.domain.auth.dto.AccessTokenDto;
import com.dopamingu.be.domain.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessTokenHeaderValue = jwtUtil.extractAccessTokenFromHeader(request);

        if (accessTokenHeaderValue != null) {
            AccessTokenDto accessTokenDto =
                    jwtTokenService.retrieveAccessToken(accessTokenHeaderValue);
            if (accessTokenDto != null) {
                jwtTokenService.setAuthenticationToken(accessTokenDto.getMemberId());
            }
        }

        filterChain.doFilter(request, response);
    }
}
