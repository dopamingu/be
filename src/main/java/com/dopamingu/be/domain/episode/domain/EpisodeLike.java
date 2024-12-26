package com.dopamingu.be.domain.episode.domain;

import com.dopamingu.be.domain.common.model.BaseTimeEntity;
import com.dopamingu.be.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    private EpisodeLikeStatus episodeLikeStatus;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public EpisodeLike(Long id, EpisodeLikeStatus episodeLikeStatus, Episode episode,
        Member member) {
        this.id = id;
        this.episodeLikeStatus = episodeLikeStatus;
        this.episode = episode;
        this.member = member;
    }
}