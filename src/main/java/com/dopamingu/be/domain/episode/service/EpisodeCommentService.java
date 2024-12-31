package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.episode.domain.ContentStatus;
import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeComment;
import com.dopamingu.be.domain.episode.dto.EpisodeCommentCreateRequest;
import com.dopamingu.be.domain.episode.repository.EpisodeCommentRepository;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.MemberStatus;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EpisodeCommentService {

    private final EpisodeCommentRepository episodeCommentRepository;
    private final EpisodeRepository episodeRepository;
    private final MemberUtil memberUtil;

    public EpisodeCommentService(EpisodeCommentRepository episodeCommentRepository,
        EpisodeRepository episodeRepository,
        MemberUtil memberUtil) {
        this.episodeCommentRepository = episodeCommentRepository;
        this.episodeRepository = episodeRepository;
        this.memberUtil = memberUtil;
    }

    public Long createEpisodeComment(Long episodeId,
        EpisodeCommentCreateRequest episodeCommentCreateRequest) {

        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // Episode 확인
        Episode episode = getEpisode(episodeId);

        EpisodeComment episodeComment = episodeCommentRepository.save(
            ofEpisodeCommentCreateRequest(episodeCommentCreateRequest, member, episode));

        return episodeComment.getId();
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

    private EpisodeComment ofEpisodeCommentCreateRequest(
        EpisodeCommentCreateRequest episodeCommentCreateRequest, Member member, Episode episode) {
        return EpisodeComment.builder()
            .contentStatus(ContentStatus.NORMAL)
            .creatorName(generateNewCreatorName(member, episode))
            .content(episodeCommentCreateRequest.getContent())
            .member(member)
            .episode(episode)
            .build();
    }

    private String generateNewCreatorName(Member member, Episode episode) {
        Optional<EpisodeComment> episodeComment = episodeCommentRepository.findFirstByMemberIdAndEpisodeId(
            member.getId(), episode.getId());
        if (episodeComment.isPresent()) {
            return episodeComment.get().getCreatorName();
        }
        return "익명 " + getDistinctMemberCount(episode.getId());
    }

    private long getDistinctMemberCount(Long episodeId) {
        return episodeCommentRepository.countDistinctMembersByEpisode(episodeId) + 1;
    }

}
