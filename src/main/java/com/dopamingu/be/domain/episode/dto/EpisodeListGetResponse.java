package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeTheme;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EpisodeListGetResponse {

    private final Long id;
    private final String episodeName;
    private final EpisodeTheme episodeTheme;
    private final String content;
    private final String addressKeyword;
    private final String thumbnailUrl;
    private final LocalDateTime createdAt;

    // TODO : 좋아요, 댓글수 추가

    public static EpisodeListGetResponse fromEntity(Episode episode) {
        return EpisodeListGetResponse.builder()
                .id(episode.getId())
                .episodeName(episode.getEpisodeName())
                .episodeTheme(episode.getEpisodeTheme())
                .content(episode.getContent())
                .addressKeyword(episode.getAddressKeyword())
                .thumbnailUrl(episode.getThumbnailUrl())
                .createdAt(episode.getCreatedAt())
                .build();
    }
}
