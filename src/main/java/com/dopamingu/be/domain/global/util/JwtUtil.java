package com.dopamingu.be.domain.global.util;

import static com.dopamingu.be.domain.common.constant.SecurityConstants.TOKEN_PREFIX;

import com.dopamingu.be.domain.auth.dto.AccessTokenDto;
import com.dopamingu.be.domain.auth.dto.RefreshTokenDto;
import com.dopamingu.be.infra.config.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(Long memberId) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
        return buildAccessToken(memberId, issuedAt, expiredAt);
    }

    public AccessTokenDto generateAccessTokenDto(Long memberId) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.accessTokenExpirationMilliTime());
        String tokenValue = buildAccessToken(memberId, issuedAt, expiredAt);
        return new AccessTokenDto(memberId, tokenValue);
    }

    public AccessTokenDto parseAccessToken(String token) throws ExpiredJwtException {
        try {
            Jws<Claims> claims = getClaims(token, getAccessTokenKey());

            return new AccessTokenDto(Long.parseLong(claims.getBody().getSubject()), token);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰인 경우에만 ExpiredJwtException 발생
            throw e;
        } catch (Exception e) {
            // 토큰 파싱에 실패하면 null 반환
            return null;
        }
    }

    public String generateRefreshToken(Long memberId) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());
        return buildRefreshToken(memberId, issuedAt, expiredAt);
    }

    public RefreshTokenDto generateRefreshTokenDto(Long memberId) {
        Date issuedAt = new Date();
        Date expiredAt =
                new Date(issuedAt.getTime() + jwtProperties.refreshTokenExpirationMilliTime());
        String tokenValue = buildRefreshToken(memberId, issuedAt, expiredAt);
        return new RefreshTokenDto(
                memberId, tokenValue, jwtProperties.getRefreshTokenExpirationTime());
    }

    private String buildAccessToken(Long memberId, Date issuedAt, Date expiredAt) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(memberId.toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getAccessTokenKey())
                .compact();
    }

    private String buildRefreshToken(Long memberId, Date issuedAt, Date expiredAt) {
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setSubject(memberId.toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(getRefreshTokenKey())
                .compact();
    }

    public RefreshTokenDto parseRefreshToken(String token) throws ExpiredJwtException {
        try {
            Jws<Claims> claims = getClaims(token, getRefreshTokenKey());

            return new RefreshTokenDto(
                    Long.parseLong(claims.getBody().getSubject()),
                    token,
                    jwtProperties.getRefreshTokenExpirationTime());
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    private Jws<Claims> getClaims(String token, Key key) {
        return Jwts.parserBuilder()
                .requireIssuer(jwtProperties.getIssuer())
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String extractAccessTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {

            return header.replace(TOKEN_PREFIX, "");
        }
        return null;
    }

    private Key getAccessTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getAccessTokenSecret().getBytes());
    }

    private Key getRefreshTokenKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getRefreshTokenSecret().getBytes());
    }
}
