package com.dopamingu.be.domain.episode.domain;

import com.dopamingu.be.domain.board.domain.Board;
import com.dopamingu.be.domain.common.model.BaseTimeEntity;
import com.dopamingu.be.domain.image.domain.EpisodeImage;
import com.dopamingu.be.domain.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.awt.Point;
import java.util.List;
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

    private String addressKeyword;

    private String address;

    @Column(columnDefinition = "geography(Point,4326)") //
    private Point locationPosition;

    private Double x;
    private Double y;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = true)
    private Board board;

    @OneToMany
    @JoinColumn(name = "episode")
    private List<EpisodeImage> imageUrlList;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 기본 게시판 설정 로직 포함-> 추후 커뮤니티 게시판 기능 고려한 설계
    // 게시판 지정되지 않은 경우, 기본 게시판 설정
    public void assignDefaultBoard(Board defaultBoard) {
        if (this.board == null) {
            this.board = defaultBoard;
        }
    }

    @Builder
    public Episode(String episodeName, EpisodeTheme episodeTheme, EpisodeStatus episodeStatus,
        String content, String addressKeyword, String address, Point locationPosition, Double x,
        Double y, Board board, Member member) {
        this.episodeName = episodeName;
        this.episodeTheme = episodeTheme;
        this.episodeStatus = EpisodeStatus.NORMAL;
        this.content = content;
        this.addressKeyword = addressKeyword;
        this.address = address;
        this.locationPosition = locationPosition;
        this.x = x;
        this.y = y;
        this.board = board;
        this.member = member;
    }


}