package com.dopamingu.be.domain.member.domain;

import com.dopamingu.be.domain.common.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username; // TODO : 남길까 말까

    @Embedded private OauthInfo oauthInfo;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus status;

    @Builder
    private Member(String username, OauthInfo oauthInfo, MemberStatus status) {
        this.username = username;
        this.oauthInfo = oauthInfo;
        this.status = status;
    }

    public static Member createMember(OauthInfo oauthInfo, String username) {
        return Member.builder()
                .username(username)
                .status(MemberStatus.NORMAL)
                .oauthInfo(oauthInfo)
                .build();
    }
}
