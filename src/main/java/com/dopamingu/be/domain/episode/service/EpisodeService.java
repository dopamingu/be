package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.board.domain.Board;
import com.dopamingu.be.domain.board.repository.BoardRepository;
import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.dto.EpisodeRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeResponse;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.image.domain.EpisodeImage;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.MemberStatus;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;

    public EpisodeService(
            EpisodeRepository episodeRepository,
            BoardRepository boardRepository,
            MemberUtil memberUtil) {
        this.episodeRepository = episodeRepository;
        this.boardRepository = boardRepository;
        this.memberUtil = memberUtil;
    }

    @Transactional
    public EpisodeResponse createEpisode(EpisodeRequest episodeRequest) {
        // 현재 접속한 회원의 유효성 확인
        Member member = memberUtil.getCurrentMember();
        if (member.getStatus().equals(MemberStatus.DELETED)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        // 에피소드 생성
        Episode episode = episodeRequest.toEpisodeEntity();

        // 기본 게시판 검색 또는 생성
        Board defaultBoard =
                boardRepository
                        .findByName("default_board")
                        .orElseGet(() -> boardRepository.save(new Board("default_board")));

        // 에피소드에 기본 게시판 할당
        episode.assignDefaultBoard(defaultBoard);

        // 이미지 리스트 저장
        List<EpisodeImage> imageList = new ArrayList<>();
        episodeRequest
                .getImageUrlList()
                .forEach(
                        imageUrl -> {
                            EpisodeImage image =
                                    EpisodeImage.builder()
                                            .imageUrl(imageUrl)
                                            .episode(episode)
                                            .build();
                            imageList.add(image);
                        });
        episode.updateImageUrlList(imageList);

        // EpisodeResponse 로 반환
        return EpisodeResponse.fromEntity(episodeRepository.save(episode));
    }
}
