package com.dopamingu.be.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UsernameRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{1,12}$", message = "문자 및 숫자만 입력 가능하며, 최대 12자리까지 허용됩니다.")
    private String username;
}
