package com.dopamingu.be.domain.member.controller.docs;

import com.dopamingu.be.domain.member.dto.request.UsernameRequestDto;
import com.dopamingu.be.domain.member.dto.response.UsernameAvailableResponseDto;
import com.dopamingu.be.domain.member.dto.response.UsernameResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "회원 관련", description = "회원 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "닉네임 중복확인", description = "사용자 요청 닉네임 중복여부 확인 API")
    public UsernameAvailableResponseDto checkUsername(@RequestParam String desiredUsername);

    @Operation(summary = "닉네임 등록", description = "사용자 닉네임 등록")
    public UsernameResponseDto updateUsername(@RequestBody UsernameRequestDto usernameRequestDto);
}
