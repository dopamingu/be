package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeImage;
import com.dopamingu.be.domain.episode.domain.EpisodeTheme;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EpisodeDetailGetResponse {

    private final Long id;
    private final String episodeName;
    private final EpisodeTheme episodeTheme;
    private final String content;

    private final String addressKeyword;
    private final String address;

    private final String thumbnailUrl;
    private final List<String> episodeImageUrlList;
    private final LocalDateTime createdAt;

    // TODO: 좋아요 수, 유저 좋아요 여부

    public static EpisodeDetailGetResponse fromEntity(Episode episode) {
        return EpisodeDetailGetResponse.builder()
            .id(episode.getId())
            .episodeName(episode.getEpisodeName())
            .episodeTheme(episode.getEpisodeTheme())
            .content(episode.getContent())
            .addressKeyword(episode.getAddressKeyword())
            .address(episode.getAddress())
            .thumbnailUrl(episode.getThumbnailUrl())
            .episodeImageUrlList(
                episode.getEpisodeImageSet().stream()
                    .map(EpisodeImage::getImageUrl)
                    .collect(Collectors.toList()))
            .createdAt(episode.getCreatedAt())
            .build();
    }
}
