package com.dopamingu.be.domain.auth.application;

import static com.dopamingu.be.domain.common.constant.SecurityConstants.KAKAO_ISSUER;
import static com.dopamingu.be.domain.common.constant.SecurityConstants.KAKAO_JWK_SET_URL;

import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.infra.config.oauth.KakaoProperties;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdTokenVerifier {
    private final KakaoProperties properties;
    private final JwtDecoder jwtDecoder = buildDecoder();

    public OidcUser getOidcUser(String idToken) {
        Jwt jwt = jwtDecoder.decode(idToken);

        OidcIdToken oidcIdToken =
                new OidcIdToken(idToken, jwt.getIssuedAt(), jwt.getExpiresAt(), jwt.getClaims());

        validateIssuer(oidcIdToken);
        validateExpired(oidcIdToken);
        validateAudience(oidcIdToken);

        List<GrantedAuthority> authorities =
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_USER")); // TODO: 왜 roles 의문임
        return new DefaultOidcUser(authorities, oidcIdToken);
    }

    private void validateExpired(OidcIdToken oidcIdToken) {
        Instant expiration = oidcIdToken.getExpiresAt();
        if (expiration.isBefore(Instant.now())) {
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
    }

    private void validateAudience(OidcIdToken oidcIdToken) {
        String clientId = oidcIdToken.getAudience().get(0);
        if (!properties.getId().equals(clientId)) {
            throw new CustomException(ErrorCode.ID_TOKEN_VERIFICATION_FAILED);
        }
    }

    private void validateIssuer(OidcIdToken oidcIdToken) {
        String issuer = oidcIdToken.getIssuer().toString();
        if (!KAKAO_ISSUER.equals(issuer)) {
            throw new CustomException(ErrorCode.ID_TOKEN_VERIFICATION_FAILED);
        }
    }

    private JwtDecoder buildDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(KAKAO_JWK_SET_URL).build();
    }
}
