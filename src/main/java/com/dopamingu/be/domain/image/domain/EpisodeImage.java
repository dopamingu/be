package com.dopamingu.be.domain.image.domain;

import com.dopamingu.be.domain.episode.domain.Episode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EpisodeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imageUrl;

    @ManyToOne
    private Episode episode;

    @Builder
    private EpisodeImage(String imageUrl, Episode episode) {
        this.imageUrl = imageUrl;
        this.episode = episode;
    }

}
