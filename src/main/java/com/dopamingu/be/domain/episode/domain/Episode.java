package com.dopamingu.be.domain.episode.domain;

import com.dopamingu.be.domain.board.domain.Board;
import com.dopamingu.be.domain.common.model.BaseTimeEntity;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateRequest;
import com.dopamingu.be.domain.global.util.PointUtil;
import com.dopamingu.be.domain.member.domain.Member;
import jakarta.persistence.CascadeType;
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
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

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
    private ContentStatus contentStatus;

    private String content;

    private String addressKeyword;

    private String address;

    @Column(columnDefinition = "POINT SRID 4326")
    private Point locationPosition;

    private Double x;
    private Double y;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String thumbnailUrl;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.PERSIST)
    private Set<EpisodeImage> episodeImageSet;

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
    public Episode(
            String episodeName,
            EpisodeTheme episodeTheme,
        ContentStatus contentStatus,
            String content,
            String addressKeyword,
            String address,
            Point locationPosition,
            Double x,
            Double y,
            Board board,
            String thumbnailUrl,
            Member member) {
        this.episodeName = episodeName;
        this.episodeTheme = episodeTheme;
        this.contentStatus = contentStatus;
        this.content = content;
        this.addressKeyword = addressKeyword;
        this.address = address;
        this.locationPosition = locationPosition;
        this.x = x;
        this.y = y;
        this.board = board;
        this.thumbnailUrl = thumbnailUrl;
        this.member = member;
    }

    // 헬퍼 메서드: EpisodeImage 추가
    public void updateEpisodeImageSet(Set<EpisodeImage> episodeImageSet) {
        this.episodeImageSet = episodeImageSet;
    }

    public void updateEpisodeInfo(EpisodeUpdateRequest episodeUpdateRequest) {
        this.episodeName = episodeUpdateRequest.getEpisodeName();
        this.episodeTheme = episodeUpdateRequest.getEpisodeTheme();
        this.content = episodeUpdateRequest.getContent();
        this.addressKeyword = episodeUpdateRequest.getAddressKeyword();
        this.address = episodeUpdateRequest.getAddress();
        this.x = episodeUpdateRequest.getX();
        this.y = episodeUpdateRequest.getY();
        this.locationPosition =
                PointUtil.createPointFromXY(
                        episodeUpdateRequest.getX(), episodeUpdateRequest.getY());
    }

    public void deleteEpisodeInfo() {
        this.contentStatus = ContentStatus.DELETED;
        this.episodeImageSet = new HashSet<>();
    }
}
