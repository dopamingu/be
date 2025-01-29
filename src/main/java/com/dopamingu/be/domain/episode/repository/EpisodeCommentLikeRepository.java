package com.dopamingu.be.domain.episode.repository;

import com.dopamingu.be.domain.episode.domain.EpisodeCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeCommentLikeRepository extends JpaRepository<EpisodeCommentLike, Long> {

}
