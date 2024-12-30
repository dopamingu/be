package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeImageRepository extends JpaRepository<EpisodeImage, Long> {

    List<EpisodeImage> findEpisodeImagesByEpisode(Episode episode);
}
