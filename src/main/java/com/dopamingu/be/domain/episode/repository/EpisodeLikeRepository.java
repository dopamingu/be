package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.EpisodeLike;
import com.dopamingu.be.domain.episode.domain.EpisodeLikeStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeLikeRepository extends JpaRepository<EpisodeLike, Long> {

    Optional<EpisodeLike> findEpisodeLikeByMemberIdAndEpisodeId(Long memberId, Long episodeId);

    Optional<EpisodeLike> findEpisodeLikeByMemberIdAndEpisodeIdAndEpisodeLikeStatus(
            Long memberId, Long episodeId, EpisodeLikeStatus episodeLikeStatus);
}
