package com.dopamingu.be.domain.episode.controller.docs;

import com.dopamingu.be.domain.episode.dto.EpisodeCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeDetailGetlResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeListGetResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "에피소드 관련", description = "에피소드 API")
public interface EpisodeControllerDocs {

    @Operation(summary = "에피소드 등록", description = "에피소드를 등록하는 API")
    public EpisodeCreateResponse createEpisode(EpisodeCreateRequest episodeCreateRequest);

    @Operation(summary = "에피소드 수정", description = "에피소드를 수정하는 API")
    public EpisodeUpdateResponse updateEpisode(
            @PathVariable Long episodeId,
            @Valid @RequestBody EpisodeUpdateRequest episodeUpdateRequest);

    @Operation(summary = "에피소드 삭제", description = "에피소드를 삭제하는 API")
    public Long deleteEpisode(@PathVariable Long episodeId);

    @Operation(summary = "에피소드 조회", description = "에피소드를 조회하는 API")
    public Slice<EpisodeListGetResponse> getEpisodeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "false") boolean isAsc);

    @Operation(summary = "에피소드 상세 조회", description = "에피소드 개별 조회하는 API")
    public EpisodeDetailGetlResponse getEpisodeDetail(@PathVariable Long episodeId);
}
