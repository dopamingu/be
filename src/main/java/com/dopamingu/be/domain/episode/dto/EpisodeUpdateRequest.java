package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.EpisodeTheme;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class EpisodeUpdateRequest {

    @NotBlank(message = "제목을 입력해주세요,")
    @Size(max = 100)
    private String episodeName;

    @Schema(description = "Episode 테마 선택", example = "FLIRTING")
    private EpisodeTheme episodeTheme;

    @NotBlank(message = "내용을 입력해주세요,")
    @Size(max = 500)
    private String content;

    @Schema(description = "썸네일 이미지 url")
    private String thumbnailUrl;

    @Schema(description = "이미지 url 전체 리스트")
    private List<String> imageUrlList;

    @Schema(description = "키워드 검색시 키워드로 나오는 주소", example = "설빙 홍대입구역점")
    private String addressKeyword;

    @Schema(description = "주소 전체", example = "서울 마포구 홍익로6길 15 상주빌딩 2층")
    private String address;

    @Schema(description = "위도", example = "37.566826")
    private Double x;

    @Schema(description = "경도", example = "126.9786567")
    private Double y;
}
