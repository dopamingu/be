package com.dopamingu.be.domain.member.application;

import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.dto.response.UsernameAvailableResponseDto;
import com.dopamingu.be.domain.member.dto.response.UsernameResponseDto;
import com.dopamingu.be.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberUtil memberUtil;

    public UsernameAvailableResponseDto checkUserNameAvailable(String desiredUsername) {
        return new UsernameAvailableResponseDto(!checkUserNameDuplicate(desiredUsername));
    }

    private boolean checkUserNameDuplicate(String desiredUsername) {
        return memberRepository.existsMemberByUsername(desiredUsername);
    }

    @Transactional
    public UsernameResponseDto updateUsername(String username) {

        Member member = memberUtil.getCurrentMember();

        if (username.equals(member.getUsername())) {
            throw new CustomException(ErrorCode.PREVIOUS_USERNAME);
        }

        if (checkUserNameDuplicate(username)) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        member.updateUsername(username);

        return new UsernameResponseDto(username);
    }
}
