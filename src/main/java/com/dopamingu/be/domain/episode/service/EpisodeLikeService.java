package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeLike;
import com.dopamingu.be.domain.episode.domain.EpisodeLikeStatus;
import com.dopamingu.be.domain.episode.repository.EpisodeLikeRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpisodeLikeService {

    private final EpisodeLikeRepository episodeLikeRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberUtil memberUtil;

    public EpisodeLikeService(EpisodeLikeRepository episodeLikeRepository,
        EpisodeRepository episodeRepository, MemberUtil memberUtil) {
        this.episodeLikeRepository = episodeLikeRepository;
        this.episodeRepository = episodeRepository;
        this.memberUtil = memberUtil;
    }

    @Transactional
    public Long likeEpisode(Long episodeId) {
        Member member = memberUtil.getCurrentMember();

        if (episodeLikeRepository.existsByMemberIdAndEpisodeId(member.getId(), episodeId)) {
            throw new CustomException(ErrorCode.DUPLICATE_EPISODE_LIKE);
        }
        Episode episode = episodeRepository.findById(episodeId)
            .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));

        EpisodeLike episodeLike = EpisodeLike.builder().member(member).episode(episode)
            .episodeLikeStatus(
                EpisodeLikeStatus.NORMAL).build();
        EpisodeLike savedEpisodeLike = episodeLikeRepository.save(episodeLike);
        return savedEpisodeLike.getId();
    }

}
