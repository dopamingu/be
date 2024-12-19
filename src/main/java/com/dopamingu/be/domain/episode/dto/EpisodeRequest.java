package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeStatus;
import com.dopamingu.be.domain.episode.domain.EpisodeTheme;
import com.dopamingu.be.domain.global.util.PointUtil;
import java.util.List;
import lombok.Getter;

@Getter
public class EpisodeRequest {

    private String episodeName;
    private EpisodeTheme episodeTheme;
    private String content;
    private List<String> imageUrlList;
    private String addressKeyword;
    private String address;
    private Double x;
    private Double y;

    public Episode toEpisodeEntity() {
        return Episode.builder()
                .episodeName(this.episodeName)
                .episodeTheme(this.episodeTheme)
                .episodeStatus(EpisodeStatus.NORMAL)
                .content(this.content)
                .addressKeyword(this.addressKeyword)
                .address(this.address)
                .locationPosition(PointUtil.createPointFromXY(this.x, this.y))
                .x(this.x)
                .y(this.y)
                .build();
    }
}
