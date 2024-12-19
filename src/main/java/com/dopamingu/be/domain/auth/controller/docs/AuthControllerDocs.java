package com.dopamingu.be.domain.auth.controller.docs;

import com.dopamingu.be.domain.auth.dto.TokenPairResponse;
import com.dopamingu.be.domain.auth.dto.TokenRefreshRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "인증 관련", description = "로그인 API")
public interface AuthControllerDocs {

    @Operation(summary = "카카오 로그인", description = "카카오 로그인 API")
    public TokenPairResponse memberOauthLoginKakao(@RequestParam String code)
            throws JsonProcessingException;

    @Operation(summary = "네이버 로그인", description = "네이버 로그인 API")
    public TokenPairResponse memberOauthLoginNaver(
            @RequestParam String code, @RequestParam String state) throws JsonProcessingException;

    @Operation(summary = "토큰 재발급", description = "refresh token 을 통한 access token 재발급")
    public TokenPairResponse refreshToken(@RequestBody TokenRefreshRequest request);

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    public ResponseEntity<Void> memberLogout();
}
