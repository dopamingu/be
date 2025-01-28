package com.dopamingu.be.domain.episode.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EpisodeCommentUpdateRequest {

    @Schema(description = "댓글 내용", example = "정말 재미있는 에피소드네요!")
    @NotBlank(message = "댓글의 내용을 입력해주세요,")
    @Size(max = 100)
    private String content;
}
