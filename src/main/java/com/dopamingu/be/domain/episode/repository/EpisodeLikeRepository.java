package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.EpisodeLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeLikeRepository extends JpaRepository<EpisodeLike, Long> {

    boolean existsByMemberIdAndEpisodeId(Long memberId, Long episodeId);

    void deleteByMemberIdAndEpisodeId(Long memberId, Long episodeId);
}