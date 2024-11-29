package com.dopamingu.be.domain.auth.controller;

import com.dopamingu.be.domain.auth.application.AuthService;
import com.dopamingu.be.domain.auth.dto.TokenPairResponse;
import com.dopamingu.be.domain.auth.dto.TokenRefreshRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public TokenPairResponse memberOauthLogin(
            @RequestParam String code, HttpServletResponse response)
            throws JsonProcessingException {
        return authService.socialLogin(code);
    }

    @PostMapping("/refresh")
    public TokenPairResponse refreshToken(@RequestBody TokenRefreshRequest request) {
        return authService.tokenRefresh(request);
    }
}
