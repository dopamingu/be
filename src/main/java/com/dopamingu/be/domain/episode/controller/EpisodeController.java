package com.dopamingu.be.domain.episode.controller;

import com.dopamingu.be.domain.episode.controller.docs.EpisodeControllerDocs;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeDetailGetlResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeListGetResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateResponse;
import com.dopamingu.be.domain.episode.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/episodes")
public class EpisodeController implements EpisodeControllerDocs {

    private final EpisodeService episodeService;

    @PostMapping
    public EpisodeCreateResponse createEpisode(
            @Valid @RequestBody EpisodeCreateRequest episodeCreateRequest) {
        return episodeService.createEpisode(episodeCreateRequest);
    }

    @PatchMapping("/{episodeId}")
    public EpisodeUpdateResponse updateEpisode(
            @PathVariable Long episodeId,
            @Valid @RequestBody EpisodeUpdateRequest episodeUpdateRequest) {
        return episodeService.updateEpisode(episodeUpdateRequest, episodeId);
    }

    @DeleteMapping("/{episodeId}")
    public Long deleteEpisode(@PathVariable Long episodeId) {
        return episodeService.deleteEpisode(episodeId);
    }

    @GetMapping("")
    public Slice<EpisodeListGetResponse> getEpisodeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "false") boolean isAsc) {
        return episodeService.getEpisodeList(page, size, sortBy, isAsc);
    }

    @GetMapping("/{episodeId}")
    public EpisodeDetailGetlResponse getEpisodeDetail(@PathVariable Long episodeId) {
        return episodeService.getEpisodeDetail(episodeId);
    }
}
