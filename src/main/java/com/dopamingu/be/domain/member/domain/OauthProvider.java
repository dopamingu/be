package com.dopamingu.be.domain.member.domain;

import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum OauthProvider {
    NAVER("naver"),
    KAKAO("kakao");

    private final String value;

    OauthProvider(String value) {
        this.value = value;
    }

    public String getKey() {
        return name();
    }

    public String getValue() {
        return value;
    }
    ;

    public static OauthProvider fromString(String oauthProviderString) {
        return Arrays.stream(OauthProvider.values())
                .filter(oauthProvider -> oauthProvider.getValue().equals(oauthProviderString))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTING_OAUTH_PROVIDER));
    }
}
