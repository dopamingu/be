package com.dopamingu.be.domain.member.controller;

import com.dopamingu.be.domain.member.application.MemberService;
import com.dopamingu.be.domain.member.controller.docs.MemberControllerDocs;
import com.dopamingu.be.domain.member.dto.request.UsernameRequestDto;
import com.dopamingu.be.domain.member.dto.response.UsernameAvailableResponseDto;
import com.dopamingu.be.domain.member.dto.response.UsernameResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController implements MemberControllerDocs {

    private final MemberService memberService;

    @GetMapping("/check-username")
    public UsernameAvailableResponseDto checkUsername(@RequestParam String desiredUsername) {
        return memberService.checkUserNameAvailable(desiredUsername);
    }

    @Override
    @PatchMapping("")
    public UsernameResponseDto updateUsername(
            @Valid @RequestBody UsernameRequestDto usernameRequestDto) {
        return memberService.updateUsername(usernameRequestDto.getUsername());
    }
}
