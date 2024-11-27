package com.dopamingu.be.domain.member.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class OauthInfo {
    private String oauthId;
    private String oauthEmail;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;

    @Builder
    public OauthInfo(String oauthId, String oauthEmail, OauthProvider provider) {
        this.oauthId = oauthId;
        this.oauthEmail = oauthEmail;
        this.provider = provider;
    }
}
