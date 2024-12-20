package com.dopamingu.be.domain.episode.controller.docs;

import com.dopamingu.be.domain.episode.dto.EpisodeCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EpisodeControllerDocs {

    @Tag(name = "에피소드 관련", description = "에피소드 API")
    @Operation(summary = "에피소드 등록", description = "에피소드를 등록하는 API")
    public EpisodeCreateResponse createEpisode(EpisodeCreateRequest episodeCreateRequest);

    @Operation(summary = "에피소드 수정", description = "에피소드를 수정하는 API")
    public EpisodeUpdateResponse updateEpisode(@PathVariable Long episodeId,
        @Valid @RequestBody EpisodeUpdateRequest episodeUpdateRequest);
}
