package com.dopamingu.be.domain.member.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.dao.MemberRepository;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.MemberStatus;
import com.dopamingu.be.domain.member.domain.OauthInfo;
import com.dopamingu.be.domain.member.domain.OauthProvider;
import com.dopamingu.be.domain.member.dto.response.UsernameAvailableResponseDto;
import com.dopamingu.be.domain.member.dto.response.UsernameResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberUtil memberUtil; // TODO: 의존성 관리 힘듬 unit-test 할떄, Spring Security가 기능이 되어야 함 --> 결합도가 높아서 나쁜 코드인듯

    @BeforeEach
    void setUp() {

    }


    @Test
    @DisplayName("유저 네임 사용 가능 - 성공")
    void successCheckUserNameAvailable() {
        // given
        String desiredUser = "dopamingu";
        when(memberRepository.existsMemberByUsername(desiredUser)).thenReturn(false);

        // when
        UsernameAvailableResponseDto res = memberService.checkUserNameAvailable(desiredUser);

        // then
        assertTrue(res.isAvailable());
    }

    @Test
    @DisplayName("유저 네임 사용 가능 - 실패")
    void failureCheckUserNameAvailable() {
        // given
        String desiredUser = "dopamingu";
        when(memberRepository.existsMemberByUsername(desiredUser)).thenReturn(true);

        // when
        UsernameAvailableResponseDto res = memberService.checkUserNameAvailable(desiredUser);

        // then
        assertFalse(res.isAvailable());
    }

    @Test
    @DisplayName("유저 네임 등록 - 성공")
    void updateUsernameSuccess() {
        // given
        String desiredUsername = "dopamingu";
        String oldUsername = "oldUsername";
        when(memberRepository.existsMemberByUsername(desiredUsername)).thenReturn(false);
        when(memberUtil.getCurrentMember()).thenReturn(createTestMember(oldUsername));

        // when
        UsernameResponseDto res = memberService.updateUsername(desiredUsername);

        // then
        assertEquals(res.getUsername(), desiredUsername);

    }

    @Test
    @DisplayName("유저 네임 등록 - 현재 있는 유저네임인 경우 실패")
    void updateUsername_failure_DUPLICATE_USERNAME() {
        // given
        String oldUsername = "oldUsername";
        String desiredUsername = "dopamingu";
        when(memberRepository.existsMemberByUsername(desiredUsername)).thenReturn(true);
        when(memberUtil.getCurrentMember()).thenReturn(createTestMember(oldUsername));

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> memberService.updateUsername(desiredUsername));

        // then
        assertEquals(ErrorCode.DUPLICATE_USERNAME, exception.getErrorCode());

    }

    @Test
    @DisplayName("유저 네임 등록 - 이전 유저네임으로 등록 하는 경우 실패")
    void updateUsername_failure_PREVIOUS_USERNAME() {
        // given
        String oldUsername = "oldUsername";
        String desiredUsername = oldUsername;

        when(memberUtil.getCurrentMember()).thenReturn(createTestMember(oldUsername));

        // when
        CustomException exception = assertThrows(CustomException.class,
            () -> memberService.updateUsername(desiredUsername));

        // then
        assertEquals(ErrorCode.PREVIOUS_USERNAME, exception.getErrorCode());

    }

    private Member createTestMember(String oldUsername) {
        OauthInfo oauthInfo = OauthInfo.builder()
            .oauthId("testId123")
            .email("test@example.com")
            .provider(OauthProvider.KAKAO)
            .build();

        return Member.builder()
            .username(oldUsername)
            .oauthInfo(oauthInfo)
            .status(MemberStatus.NORMAL)
            .build();
    }
}