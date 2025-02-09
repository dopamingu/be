package com.dopamingu.be.domain.episode.controller;

import com.dopamingu.be.domain.episode.controller.docs.EpisodeControllerDocs;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentListResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeDetailGetResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeListGetResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateResponse;
import com.dopamingu.be.domain.episode.service.EpisodeCommentLikeService;
import com.dopamingu.be.domain.episode.service.EpisodeCommentService;
import com.dopamingu.be.domain.episode.service.EpisodeLikeService;
import com.dopamingu.be.domain.episode.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
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
    private final EpisodeLikeService episodeLikeService;
    private final EpisodeCommentService episodeCommentService;
    private final EpisodeCommentLikeService episodeCommentLikeService;

    @Override
    @PostMapping
    public EpisodeCreateResponse createEpisode(
            @Valid @RequestBody EpisodeCreateRequest episodeCreateRequest) {
        return episodeService.createEpisode(episodeCreateRequest);
    }

    @Override
    @PatchMapping("/{episodeId}")
    public EpisodeUpdateResponse updateEpisode(
            @PathVariable Long episodeId,
            @Valid @RequestBody EpisodeUpdateRequest episodeUpdateRequest) {
        return episodeService.updateEpisode(episodeUpdateRequest, episodeId);
    }

    @Override
    @DeleteMapping("/{episodeId}")
    public Long deleteEpisode(@PathVariable Long episodeId) {
        return episodeService.deleteEpisode(episodeId);
    }

    @Override
    @GetMapping("")
    public Slice<EpisodeListGetResponse> getEpisodeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "false") boolean isAsc) {
        return episodeService.getEpisodeList(page, size, sortBy, isAsc);
    }

    @Override
    @GetMapping("/{episodeId}")
    public EpisodeDetailGetResponse getEpisodeDetail(@PathVariable Long episodeId) {
        return episodeService.getEpisodeDetail(episodeId);
    }

    @Override
    @PostMapping("/{episodeId}/like")
    public Long likeEpisode(@PathVariable Long episodeId) {
        return episodeLikeService.likeEpisode(episodeId);
    }

    @Override
    @PostMapping("/{episodeId}/unlike")
    public ResponseEntity<Void> unlikeEpisode(@PathVariable Long episodeId) {
        episodeLikeService.unlikeEpisode(episodeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{episodeId}/comments")
    public Long createEpisodeComment(
            @PathVariable Long episodeId,
            @Valid @RequestBody EpisodeCommentCreateRequest episodeCommentCreateRequest) {
        return episodeCommentService.createEpisodeComment(episodeId, episodeCommentCreateRequest);
    }

    @Override
    @PatchMapping("/{episodeId}/comments/{episodeCommentId}")
    public Long updateEpisodeComment(
            @PathVariable Long episodeId,
            @PathVariable Long episodeCommentId,
            @Valid @RequestBody EpisodeCommentUpdateRequest episodeCommentUpdateRequest) {
        return episodeCommentService.updateEpisodeComment(
                episodeId, episodeCommentId, episodeCommentUpdateRequest);
    }

    @Override
    @DeleteMapping("/{episodeId}/comments/{episodeCommentId}")
    public ResponseEntity<Void> deleteEpisodeComment(
            @PathVariable Long episodeId, @PathVariable Long episodeCommentId) {
        episodeCommentService.deleteEpisodeComment(episodeId, episodeCommentId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{episodeId}/comments/{episodeCommentId}/likes")
    public Long likeEpisodeComment(
            @PathVariable Long episodeId, @PathVariable Long episodeCommentId) {
        return episodeCommentLikeService.likeEpisodeComment(episodeId, episodeCommentId);
    }

    @DeleteMapping("/{episodeId}/comments/{episodeCommentId}/likes")
    public ResponseEntity<Void> unlikeEpisodeComment(
            @PathVariable Long episodeId, @PathVariable Long episodeCommentId) {
        episodeCommentLikeService.unlikeEpisodeComment(episodeId, episodeCommentId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{episodeId}/comments")
    public Slice<EpisodeCommentListResponse> getEpisodeCommentList(
            @PathVariable Long episodeId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "false") boolean isAsc) {
        return episodeCommentService.getEpisodeCommentList(episodeId, page, size, sortBy, isAsc);
    }
}
