package com.dopamingu.be.domain.common.constant;

public final class SecurityConstants {
    public static final String TOKEN_ROLE_NAME = "role";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String KAKAO_LOGIN_URL = "https://kauth.kakao.com";
    public static final String KAKAO_LOGIN_ENDPOINT = "/oauth/token";

    public static final String KAKAO_WITHDRAWAL_URL = "https://kapi.kakao.com";
    public static final String KAKAO_WITHDRAWAL_ENDPOINT = "/v1/user/unlink";
    public static final String AUTHORIZATION = "Authorization";
    public static final String KAKAO_WITHDRAWAL_URL_ENCODING = "application/x-www-form-urlencoded";
    public static final String KAKAO_ISSUER = "https://kauth.kakao.com";
    public static final String KAKAO_JWK_SET_URL = "https://kauth.kakao.com/.well-known/jwks.json";
}
