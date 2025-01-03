package com.dopamingu.be.domain.episode.domain;

import com.dopamingu.be.domain.common.model.BaseTimeEntity;
import com.dopamingu.be.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EpisodeLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_like_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private LikeStatus likeStatus;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public EpisodeLike(Long id, LikeStatus likeStatus, Episode episode, Member member) {
        this.id = id;
        this.likeStatus = likeStatus;
        this.episode = episode;
        this.member = member;
    }

    public void recreateEpisodeLike() {
        this.likeStatus = LikeStatus.NORMAL;
    }

    public void deleteEpisodeLike() {
        this.likeStatus = LikeStatus.DELETED;
    }
}
