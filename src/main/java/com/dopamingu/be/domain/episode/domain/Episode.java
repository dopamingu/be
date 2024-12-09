package com.dopamingu.be.domain.episode.domain;

import com.dopamingu.be.domain.board.domain.Board;
import com.dopamingu.be.domain.common.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.awt.Point;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Episode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Long id;

    private String episodeName;

    @Enumerated(value = EnumType.STRING)
    private EpisodeTheme episodeTheme;

    @Enumerated(value = EnumType.STRING)
    private EpisodeStatus episodeStatus;

    private String content;

    @Column(columnDefinition = "geography(Point,4326)") //
    private Point locationPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = true)
    private Board board;

    // 기본 게시판 설정 로직 포함-> 추후 커뮤니티 게시판 기능 고려한 설계
    // 게시판 지정되지 않은 경우, 기본 게시판 설정
    public void assignDefaultBoard(Board defaultBoard) {
        if (this.board == null) {
            this.board = defaultBoard;
        }
    }

    @Builder
    Episode(Long id, String episodeName, EpisodeTheme episodeTheme, EpisodeStatus episodeStatus, String content, Point locationPosition, Board board) {
        this.id = id;
        this.episodeName = episodeName;
        this.episodeTheme = episodeTheme;
        this.episodeStatus = episodeStatus;
        this.content = content;
        this.locationPosition = locationPosition;
        this.board = board;
    }

    public Episode of()

}