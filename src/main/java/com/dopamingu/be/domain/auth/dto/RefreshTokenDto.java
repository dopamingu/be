package com.dopamingu.be.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshTokenDto {
    private Long memberId;
    private String token;
    private Long ttl;
}
