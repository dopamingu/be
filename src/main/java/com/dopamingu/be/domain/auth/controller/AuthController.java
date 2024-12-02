package com.dopamingu.be.domain.auth.controller;

import com.dopamingu.be.domain.auth.application.AuthService;
import com.dopamingu.be.domain.auth.dto.TokenPairResponse;
import com.dopamingu.be.domain.auth.dto.TokenRefreshRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login/kakao")
    public TokenPairResponse memberOauthLogin(@RequestParam String code)
            throws JsonProcessingException {
        return authService.kakaoLogin(code, "kakao");
    }

    @GetMapping("/login/naver")
    public TokenPairResponse memberOauthLogin(@RequestParam String code, @RequestParam String state)
            throws JsonProcessingException {
        return authService.naverLogin(code, state, "naver");
    }

    @PostMapping("/refresh")
    public TokenPairResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        return authService.tokenRefresh(request);
    }
}
