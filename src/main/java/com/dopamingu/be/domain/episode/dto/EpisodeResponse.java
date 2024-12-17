package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.Episode;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EpisodeResponse {

    private final Long id;
    private final String episodeName;
    private final LocalDateTime createdAt;

    public static EpisodeResponse fromEntity(Episode episode) {
        return EpisodeResponse.builder()
            .id(episode.getId())
            .episodeName(episode.getEpisodeName())
            .createdAt(episode.getCreatedAt())
            .build();
    }
}
