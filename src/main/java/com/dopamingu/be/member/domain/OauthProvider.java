package com.dopamingu.be.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OauthProvider {
    APPLE("APPLE"),
    KAKAO("KAKAO");

    private String value;
}
