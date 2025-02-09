package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import com.dopamingu.be.domain.episode.domain.EpisodeCommentLike;
import com.dopamingu.be.domain.episode.domain.LikeStatus;
import com.dopamingu.be.domain.member.domain.Member;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EpisodeCommentLikeRepository extends JpaRepository<EpisodeCommentLike, Long> {

    Optional<EpisodeCommentLike> findEpisodeCommentLikeByMemberAndEpisodeComment(
            Member member, EpisodeComment episodeComment);

    Optional<EpisodeCommentLike>
            findEpisodeCommentLikeByEpisode_IdAndEpisodeComment_IdAndMember_IdAndLikeStatus(
                    Long episodeId, Long episodeCommentId, Long memberId, LikeStatus likeStatus);

    @Query(
            "SELECT ecl.episodeComment.id FROM EpisodeCommentLike ecl WHERE ecl.episode.id = :episodeId and ecl.member.id = :memberId")
    Set<Long> findEpisodeCommentLikeIds(
            @Param("episodeId") Long episodeId, @Param("memberId") Long memberId);
}
