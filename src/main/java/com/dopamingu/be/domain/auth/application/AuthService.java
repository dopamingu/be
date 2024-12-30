package com.dopamingu.be.domain.auth.application;

import com.dopamingu.be.domain.auth.dao.RefreshTokenRepository;
import com.dopamingu.be.domain.auth.dto.AccessTokenDto;
import com.dopamingu.be.domain.auth.dto.NaverUserInfoDto;
import com.dopamingu.be.domain.auth.dto.RefreshTokenDto;
import com.dopamingu.be.domain.auth.dto.TokenPairResponse;
import com.dopamingu.be.domain.auth.dto.TokenRefreshRequest;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.OauthInfo;
import com.dopamingu.be.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private static final String DEFAULT_USERNAME = "NICKNAME_NOT_REGISTERED";
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final IdTokenVerifier idTokenVerifier;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;
    private final MemberUtil memberUtil;

    public TokenPairResponse kakaoLogin(String code, String oauthProviderString)
            throws JsonProcessingException {
        String idToken = kakaoService.getIdToken(code);

        // Token 검증
        OidcUser oidcUser = idTokenVerifier.getOidcUser(idToken);

        // oauthInfo 생성하기
        OauthInfo oauthInfo = buildOicdInfo(oidcUser);

        // 기존 회원가입 여부 확인하기 -> 회원가입되지 않는다면 DB에 저장
        Member member = getMemberByOidcInfo(oauthInfo);

        // SecurityContextHolder에 인증 정보 저장
        jwtTokenService.setAuthenticationToken(member.getId());
        return getLoginResponse(member);
    }

    public TokenPairResponse naverLogin(String code, String state, String oauthProviderString)
            throws JsonProcessingException {
        String idToken = naverService.getIdToken(code, state);
        NaverUserInfoDto naverUserInfo = naverService.getNaverUserInfo(idToken);

        // oauthInfo 생성하기
        OauthInfo oauthInfo = OauthInfo.fromNaver(naverUserInfo);

        // 기존 회원가입 여부 확인하기 -> 회원가입되지 않는다면 DB에 저장
        Member member = getMemberByOidcInfo(oauthInfo);

        // SecurityContextHolder에 인증 정보 저장
        jwtTokenService.setAuthenticationToken(member.getId());
        return getLoginResponse(member);
    }

    private OauthInfo buildOicdInfo(OidcUser oidcUser) {
        return OauthInfo.fromKakao(oidcUser);
    }

    public TokenPairResponse tokenRefresh(TokenRefreshRequest request) {
        RefreshTokenDto oldRefreshTokenDto =
                jwtTokenService.validateRefreshToken(request.getRefreshToken());

        if (oldRefreshTokenDto == null) {
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        }
        RefreshTokenDto newRefreshTokenDto =
                jwtTokenService.refreshRefreshToken(oldRefreshTokenDto);
        AccessTokenDto accessTokenDto =
                jwtTokenService.refreshAccessToken(getMember(newRefreshTokenDto));
        return new TokenPairResponse(accessTokenDto.getToken(), newRefreshTokenDto.getToken());
    }

    private Member getMember(RefreshTokenDto refreshTokenDto) {
        return memberRepository
                .findById(refreshTokenDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private TokenPairResponse getLoginResponse(Member member) {
        String accessToken = jwtTokenService.createAccessToken(member.getId());
        String refreshToken = jwtTokenService.createRefreshToken(member.getId());
        return new TokenPairResponse(accessToken, refreshToken);
    }

    private Member getMemberByOidcInfo(OauthInfo oauthInfo) {
        return memberRepository
                .findByOauthInfo(oauthInfo)
                .orElseGet(
                        () ->
                                saveMember(
                                        oauthInfo,
                                        DEFAULT_USERNAME)); // TODO: Exception when there are more
        // than one member queried.
    }

    private Member saveMember(OauthInfo oauthInfo, String username) {
        Member member = Member.createMember(oauthInfo, username);
        memberRepository.save(member);
        return member;
    }

    public void memberLogout() {
        final Member currentMember = memberUtil.getCurrentMember();
        deleteRefreshToken(currentMember);
    }

    private void deleteRefreshToken(Member currentMember) {
        refreshTokenRepository
                .findById(currentMember.getId())
                .ifPresent(refreshTokenRepository::delete);
    }
}
