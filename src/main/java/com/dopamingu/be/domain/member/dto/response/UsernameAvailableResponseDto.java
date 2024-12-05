package com.dopamingu.be.domain.member.dto.response;

import lombok.Getter;

@Getter
public class UsernameAvailableResponseDto {

    private final boolean available;

    public UsernameAvailableResponseDto(boolean available) {
        this.available = available;
    }
}
