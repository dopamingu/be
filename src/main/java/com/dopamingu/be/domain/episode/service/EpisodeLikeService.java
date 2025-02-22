package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeLike;
import com.dopamingu.be.domain.episode.domain.LikeStatus;
import com.dopamingu.be.domain.episode.repository.EpisodeLikeRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpisodeLikeService {

    private final EpisodeLikeRepository episodeLikeRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberUtil memberUtil;

    public EpisodeLikeService(
            EpisodeLikeRepository episodeLikeRepository,
            EpisodeRepository episodeRepository,
            MemberUtil memberUtil) {
        this.episodeLikeRepository = episodeLikeRepository;
        this.episodeRepository = episodeRepository;
        this.memberUtil = memberUtil;
    }

    @Transactional
    public Long likeEpisode(Long episodeId) {
        Member member = memberUtil.getCurrentMember();

        Episode episode = getEpisode(episodeId);

        Optional<EpisodeLike> episodeLike =
                episodeLikeRepository.findEpisodeLikeByMemberIdAndEpisodeId(
                        member.getId(), episodeId);

        episodeLike.ifPresent(this::checkEpisodeLikeDuplicate);

        return episodeLike
                .map(
                        episodeLikeObj -> {
                            episodeLikeObj.recreateEpisodeLike();
                            return episodeLikeObj.getId();
                        })
                .orElseGet(() -> createEpisodeLike(member, episode).getId());
    }

    @Transactional
    public void unlikeEpisode(Long episodeId) {
        Member member = memberUtil.getCurrentMember();

        Episode episode = getEpisode(episodeId);
        EpisodeLike episodeLike = getEpisodeLike(member.getId(), episode.getId());
        episodeLike.deleteEpisodeLike();
    }

    private EpisodeLike createEpisodeLike(Member member, Episode episode) {
        EpisodeLike episodeLike =
                EpisodeLike.builder()
                        .member(member)
                        .episode(episode)
                        .likeStatus(LikeStatus.NORMAL)
                        .build();
        return episodeLikeRepository.save(episodeLike);
    }

    private Episode getEpisode(Long episodeId) {
        return episodeRepository
                .findById(episodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));
    }

    private void checkEpisodeLikeDuplicate(EpisodeLike episodeLike) {
        if (episodeLike.getLikeStatus().equals(LikeStatus.NORMAL)) {
            throw new CustomException(ErrorCode.DUPLICATE_EPISODE_LIKE);
        }
    }

    private EpisodeLike getEpisodeLike(Long memberId, Long episodeId) {
        return episodeLikeRepository
                .findEpisodeLikeByMemberIdAndEpisodeIdAndLikeStatus(
                        memberId, episodeId, LikeStatus.NORMAL)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_LIKE_NOT_FOUND));
    }
}
