package com.dopamingu.be.domain.episode.dto;

import com.dopamingu.be.domain.episode.domain.ContentStatus;
import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EpisodeCommentListResponse {

    private Long id;
    private String creatorName;
    private ContentStatus contentStatus;
    private String content;
    private Long likeCount;
    private boolean isLiked; // 사용자 좋아요 여부
    private List<EpisodeSubComment> subCommentList;
    private LocalDateTime createdAt;

    public static EpisodeCommentListResponse fromEntity(EpisodeComment episodeComment) {
        return EpisodeCommentListResponse.builder()
            .id(episodeComment.getId())
            .creatorName(episodeComment.getCreatorName())
            .contentStatus(episodeComment.getContentStatus())
            .likeCount(episodeComment.getLikeCount())
            .isLiked(false)
            .subCommentList(episodeComment.getSubComments().stream()
                .map(EpisodeSubComment::fromSubCommentEntity)
                .toList())
            .createdAt(episodeComment.getCreatedAt())
            .build();
    }

    public void isLikedComment() {
        this.isLiked = true;
    }

    @Getter
    @Builder
    public static class EpisodeSubComment {

        private Long id;
        private String creatorName;
        private ContentStatus contentStatus;
        private String content;
        private Long likeCount;
        private boolean isLiked; // 사용자 좋아요 여부
        private LocalDateTime createdAt;

        public static EpisodeSubComment fromSubCommentEntity(EpisodeComment subComment) {
            return EpisodeSubComment.builder()
                .id(subComment.getId())
                .creatorName(subComment.getCreatorName())
                .contentStatus(subComment.getContentStatus())
                .likeCount(subComment.getLikeCount())
                .isLiked(false)
                .createdAt(subComment.getCreatedAt())
                .build();
        }

        public void isLikedSubComment() {
            this.isLiked = true;
        }
    }

}
