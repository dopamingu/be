package com.dopamingu.be.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserInfoDto {

    private String id;
    private String email;

    public NaverUserInfoDto(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
