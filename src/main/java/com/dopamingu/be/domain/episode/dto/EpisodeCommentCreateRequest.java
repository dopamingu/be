package com.dopamingu.be.domain.episode.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EpisodeCommentCreateRequest {

    @Schema(description = "댓글 내용", example = "정말 재미있는 에피소드네요!")
    @NotBlank(message = "댓글의 내용을 입력해주세요,")
    @Size(max = 100)
    private String content;

    @Schema(description = "부모 댓글 ID. 대댓글일 경우 설정하고, 없으면 null로 넣어주세요.", example = "null")
    private Long parentId;
}
