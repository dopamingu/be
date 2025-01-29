package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.episode.repository.EpisodeCommentLikeRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeCommentRepository;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;

public class EpisodeCommentLikeService {

    private final EpisodeCommentLikeRepository episodeCommentLikeRepository;
    private final EpisodeCommentRepository episodeCommentRepository;
    private final MemberUtil memberUtil;

    public EpisodeCommentLikeService(
        EpisodeCommentLikeRepository episodeCommentLikeRepository,
        EpisodeCommentRepository episodeCommentRepository,
        MemberUtil memberUtil) {
        this.episodeCommentLikeRepository = episodeCommentLikeRepository;
        this.episodeCommentRepository = episodeCommentRepository;
        this.memberUtil = memberUtil;
    }

    public Long likeEpisodeComment(Long episodeCommentId) {
        Member member = memberUtil.getCurrentMember();

        // episodeCommentId 확인하기
        // episodeId 와 episodeCommentId 연관관계 확인하기
        // episodeCommentLike 가 있는지 검증
        // episodeCommentLike DELETED 가 있는지 검증
        return 1L;
    }
}
