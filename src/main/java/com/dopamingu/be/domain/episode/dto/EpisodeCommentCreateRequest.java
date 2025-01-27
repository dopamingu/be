package com.dopamingu.be.domain.episode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EpisodeCommentCreateRequest {

    @NotBlank(message = "댓글의 내용을 입력해주세요,")
    @Size(max = 100)
    private String content;

    private Long parentId;
}
