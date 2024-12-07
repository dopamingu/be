package com.dopamingu.be.domain.member.dto.response;

import lombok.Getter;

@Getter
public class UsernameResponseDto {

    private String username;

    public UsernameResponseDto(String username) {
        this.username = username;
    }
}
