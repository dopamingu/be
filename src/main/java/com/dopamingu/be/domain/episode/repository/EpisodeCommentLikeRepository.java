package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import com.dopamingu.be.domain.episode.domain.EpisodeCommentLike;
import com.dopamingu.be.domain.episode.domain.LikeStatus;
import com.dopamingu.be.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeCommentLikeRepository extends JpaRepository<EpisodeCommentLike, Long> {

    Optional<EpisodeCommentLike> findEpisodeCommentLikeByMemberAndEpisodeComment(
            Member member, EpisodeComment episodeComment);

    Optional<EpisodeCommentLike>
            findEpisodeCommentLikeByEpisode_IdAndEpisodeComment_IdAndMember_IdAndLikeStatus(
                    Long episodeId, Long episodeCommentId, Long memberId, LikeStatus likeStatus);
}
