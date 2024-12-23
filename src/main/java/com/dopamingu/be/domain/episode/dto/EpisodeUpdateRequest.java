package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.EpisodeTheme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class EpisodeUpdateRequest {

    @NotBlank(message = "제목을 입력해주세요,")
    @Size(max = 100)
    private String episodeName;

    private EpisodeTheme episodeTheme;

    @NotBlank(message = "내용을 입력해주세요,")
    @Size(max = 500)
    private String content;

    private String thumbnailUrl;
    private List<String> imageUrlList;
    private String addressKeyword;
    private String address;
    private Double x;
    private Double y;
}
