package com.dopamingu.be.domain.episode.domain;

import com.dopamingu.be.domain.common.model.BaseTimeEntity;
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
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EpisodeComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_comment_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ContentStatus contentStatus;

    @NotNull private String creatorName;

    @Length(max = 100) private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EpisodeComment parent = null;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<EpisodeComment> subComments = new ArrayList<>();

    @Column(name = "likes")
    private int likes = 0;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private EpisodeComment(
            ContentStatus contentStatus,
            String creatorName,
            String content,
            EpisodeComment parent,
            Episode episode,
            Member member) {
        this.contentStatus = contentStatus;
        this.creatorName = creatorName;
        this.content = content;
        this.parent = parent;
        this.episode = episode;
        this.member = member;
    }
}
