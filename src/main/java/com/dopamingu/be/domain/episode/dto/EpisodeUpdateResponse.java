package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.Episode;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EpisodeUpdateResponse {

    private final Long id;
    private final String episodeName;
    private final LocalDateTime updatedAt;

    public static EpisodeUpdateResponse fromEntity(Episode episode) {
        return EpisodeUpdateResponse.builder()
            .id(episode.getId())
            .episodeName(episode.getEpisodeName())
            .updatedAt(episode.getUpdatedAt())
            .build();
    }
}
