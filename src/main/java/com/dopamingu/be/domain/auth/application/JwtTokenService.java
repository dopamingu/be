package com.dopamingu.be.domain.auth.application;

import com.dopamingu.be.domain.auth.dao.RefreshTokenRepository;
import com.dopamingu.be.domain.auth.domain.RefreshToken;
import com.dopamingu.be.domain.auth.dto.AccessTokenDto;
import com.dopamingu.be.domain.auth.dto.RefreshTokenDto;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.JwtUtil;
import com.dopamingu.be.domain.member.domain.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 회원 ID를 사용자명으로, 비밀번호는 빈 값으로 설정한 UserDetails 객체 생성
     * 생성한 UserDetails 객체, null 자격 증명, 권한 정보를 사용해 UsernamePasswordAuthenticationToken 생성
     * 만든 인증 토큰을 SecurityContextHolder에 설정
     *
     * @param memberId : member에 id 체번을 작성
     */
    public void setAuthenticationToken(Long memberId) {
        UserDetails userDetails = User.withUsername(memberId.toString()).password("").build();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public String createAccessToken(Long memberId) {
        return jwtUtil.generateAccessToken(memberId);
    }

    public AccessTokenDto retrieveAccessToken(String accessTokenValue) {
        try {
            return jwtUtil.parseAccessToken(accessTokenValue);
        } catch (Exception e) {
            return null;
        }
    }

    public String createRefreshToken(Long memberId) {
        String token = jwtUtil.generateRefreshToken(memberId);
        RefreshToken refreshToken = RefreshToken.builder().memberId(memberId).token(token).build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public RefreshTokenDto refreshRefreshToken(RefreshTokenDto oldRefreshTokenDto) {
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findById(oldRefreshTokenDto.getMemberId())
                        .orElseThrow(() -> new CustomException(ErrorCode.MISSING_JWT_TOKEN));
        RefreshTokenDto refreshTokenDto =
                jwtUtil.generateRefreshTokenDto(refreshToken.getMemberId());
        refreshToken.updateRefreshToken(refreshTokenDto.getToken());
        return refreshTokenDto;
    }

    public RefreshTokenDto refreshRefreshTestToken(Long memberId) {

        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findById(memberId);

        // 새로운 RefreshTokenDto 를 생성한다.
        RefreshTokenDto refreshTokenDto = jwtUtil.generateRefreshTokenDto(memberId);
        RefreshToken refreshToken;
        // 존재한다면,
        if (optionalRefreshToken.isPresent()) {
            refreshToken = optionalRefreshToken.get();
            refreshToken.updateRefreshToken(refreshTokenDto.getToken());
        } else {
            refreshToken =
                    RefreshToken.builder()
                            .memberId(memberId)
                            .token(refreshTokenDto.getToken())
                            .build();
        }

        refreshTokenRepository.save(refreshToken);

        return refreshTokenDto;
    }

    public AccessTokenDto refreshAccessToken(Member member) {
        return jwtUtil.generateAccessTokenDto(member.getId());
    }

    public RefreshTokenDto validateRefreshToken(String refreshToken) {
        return jwtUtil.parseRefreshToken(refreshToken);
    }
}
