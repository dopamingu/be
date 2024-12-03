package com.dopamingu.be.domain.member.domain;

import org.junit.jupiter.api.Test;

class OauthProviderTest {

    @Test
    void getKey() {
        OauthProvider oauthProvider = OauthProvider.KAKAO;
        System.out.println("oauthProvider.getKey() = " + oauthProvider.getKey());
    }

    @Test
    void getValue() {
        OauthProvider oauthProvider = OauthProvider.KAKAO;
        System.out.println("platform.getValue() = " + oauthProvider.getValue());
    }

    @Test
    void fromString() {
        OauthProvider oauthProvider = OauthProvider.fromString(String.valueOf("kakao"));
        System.out.println(
                "oauthProvider.equals(OAuthProvider.KAKAO) = "
                        + oauthProvider.equals(OauthProvider.KAKAO));
    }
}
