package com.dopamingu.be.domain.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "DP4000", "Sample Error Message"),
    NOT_EXISTING_OAUTH_PROVIDER(HttpStatus.BAD_REQUEST, "DP4001", "존재하지 않는 소셜로그인 플랫폼입니다."),

    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "DP4010", "만료된 JWT 토큰입니다."),
    ID_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "DP4011", "ID 토큰 검증에 실패했습니다."),
    NOT_EXISTED_ROLE(HttpStatus.UNAUTHORIZED, "DP4012", "존재하지 않는 역할입니다."),
    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "DP4013", "시큐리티 인증 정보를 찾을 수 없습니다."),
    MISSING_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "DP4014", "토큰 정보가 존재하지 않습니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "DP4040", "해당 회원을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "DP4050", "잘못된 HTTP 메서드입니다."),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "DP4220", "요청 데이터 형식이 올바르지 않습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DP500", "서버 에러가 발생했습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
