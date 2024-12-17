package com.dopamingu.be.domain.episode.controller.docs;

import com.dopamingu.be.domain.episode.dto.EpisodeRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

public interface EpisodeControllerDocs {

    @Tag(name = "에피소드 관련", description = "에피소드 API")

    @Operation(summary = "에피소드 등록", description = "에피소드를 등록하는 API")
    public EpisodeResponse createEpisode(EpisodeRequest episodeRequest);
}
