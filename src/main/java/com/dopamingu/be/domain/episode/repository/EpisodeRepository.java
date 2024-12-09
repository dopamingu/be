package com.dopamingu.be.domain.episode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dopamingu.be.domain.episode.domain.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
