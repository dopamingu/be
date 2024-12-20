package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.EpisodeTheme;
import java.util.List;
import lombok.Getter;

@Getter
public class EpisodeUpdateRequest {

    private String episodeName;
    private EpisodeTheme episodeTheme;
    private String content;
    private String thumbnailUrl;
    private List<String> imageUrlList;
    private String addressKeyword;
    private String address;
    private Double x;
    private Double y;
}
