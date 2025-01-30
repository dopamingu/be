package com.dopamingu.be.domain.episode.controller.docs;

import com.dopamingu.be.domain.episode.dto.EpisodeCommentCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeDetailGetResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeListGetResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "에피소드 관련", description = "에피소드 API")
public interface EpisodeControllerDocs {

    @Operation(summary = "에피소드 등록", description = "에피소드를 등록하는 API")
    public EpisodeCreateResponse createEpisode(
            @Parameter(description = "에피소드 생성 요청 정보") @Valid @RequestBody
                    EpisodeCreateRequest episodeCreateRequest);

    @Operation(summary = "에피소드 수정", description = "에피소드를 수정하는 API")
    public EpisodeUpdateResponse updateEpisode(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId,
            @Valid @RequestBody EpisodeUpdateRequest episodeUpdateRequest);

    @Operation(summary = "에피소드 삭제", description = "에피소드를 삭제하는 API")
    public Long deleteEpisode(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId);

    @Operation(summary = "에피소드 조회", description = "에피소드를 조회하는 API")
    public Slice<EpisodeListGetResponse> getEpisodeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "false") boolean isAsc);

    @Operation(summary = "에피소드 상세 조회", description = "에피소드 개별 조회하는 API")
    public EpisodeDetailGetResponse getEpisodeDetail(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId);

    @Operation(summary = "에피소드 좋아요", description = "에피소드를 좋아요 등록하는 API")
    public Long likeEpisode(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId);

    @Operation(summary = "에피소드 좋아요 해제", description = "특정 에피소드의 좋아요를 해제합니다.")
    @PostMapping("/{episodeId}/unlike")
    public ResponseEntity<Void> unlikeEpisode(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId);

    @Operation(summary = "에피소드 댓글 생성", description = "특정 에피소드에 댓글을 생성합니다.")
    @PostMapping("/{episodeId}/comments")
    Long createEpisodeComment(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId,
            @Valid @RequestBody EpisodeCommentCreateRequest episodeCommentCreateRequest);

    @Operation(summary = "에피소드 댓글 수정", description = "특정 에피소드에 이미 존재하는 댓글을 수정합니다.")
    @PatchMapping("/{episodeId}/comments/{episodeCommentId}")
    Long updateEpisodeComment(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId,
            @Parameter(name = "episodeCommentId", description = "에피소드 댓글 ID") @PathVariable
                    Long episodeCommentId,
            @Valid @RequestBody EpisodeCommentUpdateRequest episodeCommentUpdateRequest);

    @Operation(summary = "에피소드 댓글 삭제", description = "특정 에피소드에 이미 존재하는 댓글을 삭제합니다.")
    @DeleteMapping("/{episodeId}/comments/{episodeCommentId}")
    ResponseEntity<Void> deleteEpisodeComment(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId,
            @Parameter(name = "episodeCommentId", description = "에피소드 댓글 ID") @PathVariable
                    Long episodeCommentId);

    @Operation(summary = "에피소드 댓글 좋아요", description = "특정 에피소드 댓글에 대한 좋아요를 생성합니다.")
    @PostMapping("/{episodeId}/comments/{episodeCommentId}/likes")
    public Long likeEpisodeComment(
            @Parameter(name = "episodeId", description = "에피소드 ID") @PathVariable Long episodeId,
            @Parameter(name = "episodeCommentId", description = "에피소드 댓글 ID") @PathVariable
                    Long episodeCommentId);
}
