package com.dopamingu.be.domain.member.application;

import com.dopamingu.be.domain.member.dao.MemberRepository;
import com.dopamingu.be.domain.member.dto.response.UsernameAvailableResponseDto;
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

    public UsernameAvailableResponseDto checkUsernameDuplicate(String desiredUsername) {
        return new UsernameAvailableResponseDto(
            !memberRepository.existsMemberByUsername(desiredUsername));
    }
}
