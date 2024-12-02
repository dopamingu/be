package com.dopamingu.be.domain.member.domain;

import static com.dopamingu.be.domain.member.domain.OauthProvider.KAKAO;
import static com.dopamingu.be.domain.member.domain.OauthProvider.NAVER;

import com.dopamingu.be.domain.auth.dto.NaverUserInfoDto;
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
    private String email;

    @Enumerated(EnumType.STRING)
    private OauthProvider provider;

    @Builder
    public OauthInfo(String oauthId, String email, OauthProvider provider) {
        this.oauthId = oauthId;
        this.email = email;
        this.provider = provider;
    }

    public static OauthInfo fromKakao(OidcUser user) {
        return OauthInfo.builder()
                .oauthId(user.getSubject())
                .email("DEFAULT")
                .provider(KAKAO)
                .build();
    }

    public static OauthInfo fromNaver(NaverUserInfoDto user) {
        return OauthInfo.builder()
                .oauthId(user.getId())
                .provider(NAVER)
                .email(user.getEmail())
                .build();
    }
}
