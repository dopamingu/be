package com.dopamingu.be.domain.auth.controller;

import com.dopamingu.be.domain.auth.application.AuthService;
import com.dopamingu.be.domain.auth.controller.docs.AuthControllerDocs;
import com.dopamingu.be.domain.auth.dto.TokenPairResponse;
import com.dopamingu.be.domain.auth.dto.TokenRefreshRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {
    private final AuthService authService;

    @GetMapping("/login/kakao")
    public TokenPairResponse memberOauthLoginKakao(@RequestParam String code)
            throws JsonProcessingException {
        return authService.kakaoLogin(code, "kakao");
    }

    @GetMapping("/login/naver")
    public TokenPairResponse memberOauthLoginNaver(
        @RequestParam String code, @RequestParam String state) throws JsonProcessingException {
        return authService.naverLogin(code, state, "naver");
    }

    @PostMapping("/refresh")
    public TokenPairResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        return authService.tokenRefresh(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> memberLogout() {
        authService.memberLogout();
        return ResponseEntity.ok().build();
    }
}
