package com.dopamingu.be.domain.episode.controller;

import com.dopamingu.be.domain.episode.dto.EpisodeRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeResponse;
import com.dopamingu.be.domain.episode.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    public EpisodeResponse createEpisode(@RequestBody EpisodeRequest episodeRequest) {
        return episodeService.createEpisode(episodeRequest);
    }
}
