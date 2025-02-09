package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.ContentStatus;
import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EpisodeCommentRepository extends JpaRepository<EpisodeComment, Long> {

    Optional<EpisodeComment> findFirstByMemberIdAndEpisodeId(Long memberId, Long episodeId);

    @Query(
            "SELECT COUNT(DISTINCT ec.member.id) FROM EpisodeComment ec WHERE ec.episode.id = :episodeId and ec.creatorName != '작성자'")
    long countDistinctMembersByEpisode(@Param("episodeId") Long episodeId);

    Optional<EpisodeComment> findByIdAndContentStatus(
            Long episodeCommentId, ContentStatus contentStatus);

    Optional<EpisodeComment> findByIdAndContentStatusAndEpisodeAndParent(
            Long episodeSubCommentId,
            ContentStatus contentStatus,
            Episode episode,
            EpisodeComment episodeComment);

    Page<EpisodeComment> findAllByEpisodeIdAndParentIsNull(Pageable pageable, Long episodeId);

}
