package com.dopamingu.be.domain.member.domain;

import static com.dopamingu.be.domain.member.domain.OauthProvider.KAKAO;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Embeddable
@Getter
@NoArgsConstructor
public class OauthInfo {
    private String oauthId;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;

    @Builder
    public OauthInfo(String oauthId, OauthProvider provider) {
        this.oauthId = oauthId;
        this.provider = provider;
    }

    public static OauthInfo fromKakao(OidcUser user) {
        return OauthInfo.builder().oauthId(user.getSubject()).provider(KAKAO).build();
    }
}
