package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import com.dopamingu.be.domain.episode.domain.EpisodeCommentLike;
import com.dopamingu.be.domain.episode.domain.LikeStatus;
import com.dopamingu.be.domain.episode.repository.EpisodeCommentLikeRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeCommentRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EpisodeCommentLikeService {

    private final EpisodeCommentLikeRepository episodeCommentLikeRepository;
    private final EpisodeCommentRepository episodeCommentRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberUtil memberUtil;

    public EpisodeCommentLikeService(
            EpisodeCommentLikeRepository episodeCommentLikeRepository,
            EpisodeCommentRepository episodeCommentRepository,
            EpisodeRepository episodeRepository,
            MemberUtil memberUtil) {
        this.episodeCommentLikeRepository = episodeCommentLikeRepository;
        this.episodeCommentRepository = episodeCommentRepository;
        this.episodeRepository = episodeRepository;
        this.memberUtil = memberUtil;
    }

    public Long likeEpisodeComment(Long episodeId, Long episodeCommentId) {
        Member member = memberUtil.getCurrentMember();

        Episode episode = getValidEpisode(episodeId);

        // episodeCommentId 확인 하기
        EpisodeComment episodeComment = getValidEpisodeComment(episodeCommentId);

        // episodeId 와 episodeCommentId 연관 관계 확인 하기
        checkEpisodeId(episodeId, episodeComment);

        // episodeCommentLike 가 있는지 검증
        Optional<EpisodeCommentLike> episodeCommentLike =
                episodeCommentLikeRepository.findEpisodeCommentLikeByMemberAndEpisodeComment(
                        member, episodeComment);

        // episodeCommentLike DELETED 가 있는지 검증
        episodeCommentLike.ifPresent(this::checkEpisodeCommentLikeDuplicate);
        episodeCommentLike
                .map(
                        episodeCommentLikeObj -> {
                            episodeCommentLikeObj.recreateEpisodeCommentLike();
                            return episodeCommentLikeObj;
                        })
                .orElseGet(() -> createEpisodeCommentLike(member, episodeComment, episode));

        // episodeComment 의 likes 숫자 +1
        episodeComment.increaseLikeCount();

        return episodeComment.getId();
    }

    public void unlikeEpisodeComment(Long episodeId, Long episodeCommentId) {
        Member member = memberUtil.getCurrentMember();

        // 있으면, DELETED 로 상태를 변경합니다.
        EpisodeCommentLike episodeCommentLike =
                getEpisodeCommentLike(episodeId, episodeCommentId, member.getId());
        episodeCommentLike.deleteEpisodeCommentLike();

        // episodeComment 의 likes 숫자 -1
        EpisodeComment episodeComment = episodeCommentLike.getEpisodeComment();
        episodeComment.decreaseLikeCount();
    }

    private Episode getValidEpisode(Long episodeId) {
        return episodeRepository
                .findById(episodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));
    }

    private EpisodeComment getValidEpisodeComment(Long episodeCommentId) {
        return episodeCommentRepository
                .findById(episodeCommentId)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_COMMENT_NOT_FOUND));
    }

    private void checkEpisodeId(Long episodeId, EpisodeComment episodeComment) {
        if (!episodeId.equals(episodeComment.getEpisode().getId())) {
            throw new CustomException(ErrorCode.EPISODE_COMMENT_RELATION_NOT_FOUND);
        }
    }

    private void checkEpisodeCommentLikeDuplicate(EpisodeCommentLike episodeCommentLike) {
        if (episodeCommentLike.getLikeStatus().equals(LikeStatus.NORMAL)) {
            throw new CustomException(ErrorCode.DUPLICATE_EPISODE_COMMENT_LIKE);
        }
    }

    private EpisodeCommentLike createEpisodeCommentLike(
            Member member, EpisodeComment episodeComment, Episode episode) {
        EpisodeCommentLike episodeCommentLike =
                EpisodeCommentLike.builder()
                        .episode(episode)
                        .episodeComment(episodeComment)
                        .member(member)
                        .likeStatus(LikeStatus.NORMAL)
                        .build();
        return episodeCommentLikeRepository.save(episodeCommentLike);
    }

    private EpisodeCommentLike getEpisodeCommentLike(
            Long episodeId, Long episodeCommentId, Long memberId) {
        return episodeCommentLikeRepository
                .findEpisodeCommentLikeByEpisode_IdAndEpisodeComment_IdAndMember_IdAndLikeStatus(
                        episodeId, episodeCommentId, memberId, LikeStatus.NORMAL)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_COMMENT_LIKE_NOT_FOUND));
    }
}
