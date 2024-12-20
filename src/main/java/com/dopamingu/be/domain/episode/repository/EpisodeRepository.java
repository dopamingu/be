package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {}
