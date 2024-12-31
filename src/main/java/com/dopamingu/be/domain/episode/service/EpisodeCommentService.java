package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.episode.domain.ContentStatus;
import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentUpdateRequest;
import com.dopamingu.be.domain.episode.repository.EpisodeCommentRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.MemberStatus;
import java.util.Optional;
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
        Episode episode = getEpisode(episodeId);

        EpisodeComment episodeComment =
                episodeCommentRepository.save(
                        ofEpisodeCommentCreateRequest(
                                episodeCommentCreateRequest, member, episode));

        return episodeComment.getId();
    }

    public Long updateEpisodeComment(
            Long episodeId,
            Long episodeCommentId,
            EpisodeCommentUpdateRequest episodeCommentUpdateRequest) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // Episode 확인
        Episode episode = getEpisode(episodeId);

        // EpisodeComment 확인
        EpisodeComment episodeComment = getEpisodeComment(episodeCommentId);

        checkRequestorIsCreator(episodeComment, member);

        // 내용 변경
        episodeComment.updateEpisodeComment(episodeCommentUpdateRequest);

        return episodeComment.getId();
    }

    public void deleteEpisodeComment(Long episodeId, Long episodeCommentId) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // Episode 확인
        Episode episode = getEpisode(episodeId);

        // EpisodeComment 확인
        EpisodeComment episodeComment = getEpisodeComment(episodeCommentId);

        checkRequestorIsCreator(episodeComment, member);

        // 삭제 처리
        episodeComment.deleteEpisodeComment();
    }

    public Long createEpisodeSubComment(
            Long episodeId,
            Long episodeCommentId,
            EpisodeCommentCreateRequest episodeCommentCreateRequest) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // Episode 확인
        Episode episode = getEpisode(episodeId);

        // EpisodeComment 확인
        EpisodeComment episodeComment = getEpisodeComment(episodeCommentId);

        EpisodeComment episodeSubComment =
                episodeCommentRepository.save(
                        buildSubComment(
                                episodeCommentCreateRequest, member, episode, episodeComment));

        return episodeSubComment.getId();
    }

    private void checkMemberStatus(Member member) {
        // 현재 접속한 회원의 유효성 확인
        if (member.getStatus().equals(MemberStatus.DELETED)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private Episode getEpisode(Long episodeId) {
        return episodeRepository
                .findById(episodeId)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));
    }

    private EpisodeComment getEpisodeComment(Long episodeId) {
        return episodeCommentRepository
                .findByIdAndContentStatus(episodeId, ContentStatus.NORMAL)
                .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_COMMENT_NOT_FOUND));
    }

    private void checkRequestorIsCreator(EpisodeComment episodeComment, Member member) {
        if (!episodeComment.getMember().equals(member)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    private EpisodeComment ofEpisodeCommentCreateRequest(
            EpisodeCommentCreateRequest episodeCommentCreateRequest,
            Member member,
            Episode episode) {
        return EpisodeComment.builder()
                .contentStatus(ContentStatus.NORMAL)
                .creatorName(generateNewCreatorName(member, episode))
                .content(episodeCommentCreateRequest.getContent())
                .member(member)
                .episode(episode)
                .build();
    }

    private EpisodeComment buildSubComment(
            EpisodeCommentCreateRequest episodeCommentCreateRequest,
            Member member,
            Episode episode,
            EpisodeComment episodeComment) {
        return EpisodeComment.builder()
                .contentStatus(ContentStatus.NORMAL)
                .creatorName(generateNewCreatorName(member, episode))
                .content(episodeCommentCreateRequest.getContent())
                .member(member)
                .episode(episode)
                .parent(episodeComment)
                .build();
    }
    ;

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
}
