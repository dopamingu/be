package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.episode.domain.ContentStatus;
import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentListResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentUpdateRequest;
import com.dopamingu.be.domain.episode.repository.EpisodeCommentRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.MemberStatus;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EpisodeCommentService {

    private final EpisodeCommentRepository episodeCommentRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberUtil memberUtil;
    private static final String EPISODE_CREATOR_NAME = "작성자";
    private static final String PREFIX_CREATOR_NAME = "익명";

    public EpisodeCommentService(
            EpisodeCommentRepository episodeCommentRepository,
            EpisodeRepository episodeRepository,
            MemberUtil memberUtil) {
        this.episodeCommentRepository = episodeCommentRepository;
        this.episodeRepository = episodeRepository;
        this.memberUtil = memberUtil;
    }

    public Long createEpisodeComment(
            Long episodeId, EpisodeCommentCreateRequest episodeCommentCreateRequest) {

        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // Episode 확인
        Episode episode = getValidEpisode(episodeId);

        // ParentComment 확인
        EpisodeComment parentComment = null;
        if (episodeCommentCreateRequest.getParentId() != null) {
            parentComment = getValidParentComment(episodeCommentCreateRequest.getParentId());
        }

        EpisodeComment episodeComment =
                episodeCommentRepository.save(
                        ofEpisodeCommentCreateRequest(
                                episodeCommentCreateRequest, member, episode, parentComment));

        return episodeComment.getId();
    }

    public Long updateEpisodeComment(
            Long episodeId,
            Long episodeCommentId,
            EpisodeCommentUpdateRequest episodeCommentUpdateRequest) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        EpisodeComment episodeComment =
                getEpisodeCommentForUpdate(episodeId, episodeCommentId, member);
        episodeComment.updateCommentContent(episodeCommentUpdateRequest);

        return episodeCommentId;
    }

    public void deleteEpisodeComment(Long episodeId, Long episodeCommentId) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // Episode 확인
        EpisodeComment episodeComment =
                getEpisodeCommentForUpdate(episodeId, episodeCommentId, member);

        // 삭제 처리
        episodeComment.deleteEpisodeComment();
    }

    public Slice<EpisodeCommentListResponse> getEpisodeCommentList(Long episodeId, int page,
        int size, String sortBy, boolean isAsc) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();

        // episode 상태 확인
        Episode episode = getValidEpisode(episodeId);

        // episode comment 만 먼저 찾기
        Slice<EpisodeComment> comments = episodeCommentRepository.findAllByEpisodeIdAndParentIsNull(
            getPageable(page, size, sortBy, isAsc), episode.getId());

        // sub commment 조회가능

        // parentId 없는 경우만 조회하기

        // parnetId 로 넣기 

        // episode subComment 묶어넣기
        // 해당 유저의 like 여부 조회하기

        return null;
    }

    private EpisodeComment ofEpisodeCommentCreateRequest(
            EpisodeCommentCreateRequest episodeCommentCreateRequest,
            Member member,
            Episode episode,
            EpisodeComment parentComment) {
        return EpisodeComment.builder()
                .contentStatus(ContentStatus.NORMAL)
                .creatorName(generateNewCreatorName(member, episode))
                .content(episodeCommentCreateRequest.getContent())
                .member(member)
                .episode(episode)
                .parent(parentComment)
                .build();
    }

    private String generateNewCreatorName(Member member, Episode episode) {
        if (episode.getMember().equals(member)) {

            return EPISODE_CREATOR_NAME;
        }
        Optional<EpisodeComment> episodeComment =
                episodeCommentRepository.findFirstByMemberIdAndEpisodeId(
                        member.getId(), episode.getId());

        if (episodeComment.isPresent()) {
            return episodeComment.get().getCreatorName();
        }
        return PREFIX_CREATOR_NAME + getDistinctMemberCount(episode.getId());
    }

    private long getDistinctMemberCount(Long episodeId) {
        return episodeCommentRepository.countDistinctMembersByEpisode(episodeId) + 1;
    }

    /**
     * ---------------------------------------------- 아래부터는 중복되는 검증 로직을 모아둔 private 메서드들
     * ----------------------------------------------
     */
    private void checkMemberStatus(Member member) {
        // 현재 접속한 회원의 유효성 확인
        if (member.getStatus().equals(MemberStatus.DELETED)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private Episode getValidEpisode(Long episodeId) {
        return episodeRepository
            .findEpisodeByIdAndContentStatus(episodeId, ContentStatus.NORMAL)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));
    }

    private EpisodeComment getValidParentComment(Long episodeCommentId) {
        return episodeCommentRepository
                .findById(episodeCommentId)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));
    }

    /**
     * 댓글 수정/삭제 시 필요한 "공통 검증"을 담당하는 메서드 - episodeId로 Episode를 찾고 - commentId로 댓글을 찾은 뒤 - 댓글이 해당
     * episode에 속해 있는지 - 이미 삭제(status=DELETED)된 댓글인지 - 작성자(writer)와 현재 사용자와 동일한지
     */
    private EpisodeComment getEpisodeCommentForUpdate(
            Long episodeId, Long commentId, Member creator) {
        // (1) 댓글 조회
        EpisodeComment comment =
                episodeCommentRepository
                        .findById(commentId)
                        .orElseThrow(
                                () -> new CustomException(ErrorCode.EPISODE_COMMENT_NOT_FOUND));

        // (2) 에피소드 일치 여부
        if (!comment.getEpisode().getId().equals(episodeId)) {
            throw new CustomException(ErrorCode.EPISODE_COMMENT_RELATION_NOT_FOUND);
        }
        // (3) 상태 확인
        if (comment.getContentStatus() == ContentStatus.DELETED) {
            throw new CustomException(ErrorCode.RESOURCE_DELETED);
        }
        // (4) 작성자와 수정/삭제자와 일치 확인
        checkRequestorIsCreator(comment, creator);

        return comment;
    }

    private void checkRequestorIsCreator(EpisodeComment episodeComment, Member member) {
        if (!episodeComment.getMember().equals(member)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    private Pageable getPageable(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page - 1, size, sort);
    }

}
