package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeStatus;
import com.dopamingu.be.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    Optional<Episode> findEpisodeByIdAndMember(Long episodeId, Member member);

    Page<Episode> findAllByEpisodeStatus(Pageable pageable, EpisodeStatus episodeStatus);

    Optional<Episode> findEpisodeByIdAndEpisodeStatus(Long episodeId, EpisodeStatus episodeStatus);
}
