package com.dopamingu.be.domain.episode.service;

import com.dopamingu.be.domain.board.domain.Board;
import com.dopamingu.be.domain.board.repository.BoardRepository;
import com.dopamingu.be.domain.episode.domain.ContentStatus;
import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeCreateResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeDetailGetlResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeListGetResponse;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateRequest;
import com.dopamingu.be.domain.episode.dto.EpisodeUpdateResponse;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;
import com.dopamingu.be.domain.global.error.exception.CustomException;
import com.dopamingu.be.domain.global.error.exception.ErrorCode;
import com.dopamingu.be.domain.global.util.MemberUtil;
import com.dopamingu.be.domain.global.util.PointUtil;
import com.dopamingu.be.domain.image.domain.EpisodeImage;
import com.dopamingu.be.domain.image.service.EpisodeImageService;
import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.MemberStatus;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final BoardRepository boardRepository;
    private final MemberUtil memberUtil;
    private final EpisodeImageService episodeImageService;

    public EpisodeService(
            EpisodeRepository episodeRepository,
            BoardRepository boardRepository,
            MemberUtil memberUtil,
            EpisodeImageService episodeImageService) {
        this.episodeRepository = episodeRepository;
        this.boardRepository = boardRepository;
        this.memberUtil = memberUtil;
        this.episodeImageService = episodeImageService;
    }

    @Transactional
    public EpisodeCreateResponse createEpisode(EpisodeCreateRequest episodeCreateRequest) {
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // 에피소드 생성
        Episode episode = ofEpisodeCreateRequest(episodeCreateRequest, member);
        Episode savedEpisode = episodeRepository.save(episode);

        assignDefaultBoard(episode);

        // EpisodeUrlSet 저장
        saveEpisodeImageList(episodeCreateRequest.getImageUrlList(), episode);

        // EpisodeResponse 로 반환
        return EpisodeCreateResponse.fromEntity(savedEpisode);
    }

    private void saveEpisodeImageList(List<String> imageUrlList, Episode episode) {
        if (imageUrlList != null && !imageUrlList.isEmpty()) {
            Set<EpisodeImage> episodeImageSet =
                    episodeImageService.createMultipleEpisodeImage(episode, imageUrlList);
            // episode 의 episodeImageSet 필드 업데이트
            episode.updateEpisodeImageSet(episodeImageSet);
        }
    }

    @Transactional
    public EpisodeUpdateResponse updateEpisode(
            EpisodeUpdateRequest episodeUpdateRequest, Long episodeId) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // 회원 ID, 에피소드 ID 로 episode 찾기
        Episode episode =
                episodeRepository
                        .findEpisodeByIdAndMember(episodeId, member)
                        .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));

        // Episode 유효성 확인
        if (!episode.getContentStatus().equals(ContentStatus.NORMAL)) {
            throw new CustomException(ErrorCode.EPISODE_NOT_FOUND);
        }
        // 이미지 수정
        episodeImageService.deleteRelatedEpisodeImages(episode);
        saveEpisodeImageList(episodeUpdateRequest.getImageUrlList(), episode);

        episode.updateEpisodeInfo(episodeUpdateRequest);

        return EpisodeUpdateResponse.fromEntity(episode);
    }

    public Long deleteEpisode(Long episodeId) {
        // 회원 확인
        Member member = memberUtil.getCurrentMember();
        checkMemberStatus(member);

        // 회원 ID, 에피소드 ID 로 episode 찾기
        Episode episode =
                episodeRepository
                        .findEpisodeByIdAndMember(episodeId, member)
                        .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));
        // Episode 유효성 확인
        if (!episode.getContentStatus().equals(ContentStatus.NORMAL)) {
            throw new CustomException(ErrorCode.EPISODE_NOT_FOUND);
        }
        // 이미지 삭제, 에피소드 삭제 처리
        episodeImageService.deleteRelatedEpisodeImages(episode);
        episode.deleteEpisodeInfo();

        return episodeId;
    }

    public Slice<EpisodeListGetResponse> getEpisodeList(
            int page, int size, String sortBy, boolean isAsc) {
        Slice<Episode> sliceList =
                episodeRepository.findAllByContentStatus(
                        getPageable(page, size, sortBy, isAsc), ContentStatus.NORMAL);
        return sliceList.map(EpisodeListGetResponse::fromEntity);
    }

    // 특정 Episode 의 정보 가져 오기
    // 예외 처리 없는 에피소드를 조회 하면 예외 발생
    public EpisodeDetailGetlResponse getEpisodeDetail(Long episodeId) {
        Episode episode =
                episodeRepository
                    .findEpisodeByIdAndContentStatus(episodeId, ContentStatus.NORMAL)
                        .orElseThrow(() -> new CustomException(ErrorCode.EPISODE_NOT_FOUND));
        return EpisodeDetailGetlResponse.fromEntity(episode);
    }

    private void checkMemberStatus(Member member) {
        // 현재 접속한 회원의 유효성 확인
        if (member.getStatus().equals(MemberStatus.DELETED)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private Episode ofEpisodeCreateRequest(EpisodeCreateRequest request, Member member) {
        return Episode.builder()
                .episodeName(request.getEpisodeName())
                .episodeTheme(request.getEpisodeTheme())
                .contentStatus(ContentStatus.NORMAL)
                .content(request.getContent())
                .addressKeyword(request.getAddressKeyword())
                .address(request.getAddress())
                .locationPosition(PointUtil.createPointFromXY(request.getX(), request.getY()))
                .x(request.getX())
                .y(request.getY())
                .thumbnailUrl(request.getThumbnailUrl())
                .member(member)
                .build();
    }

    private void assignDefaultBoard(Episode episode) {
        // 기본 게시판 검색 또는 생성
        Board defaultBoard =
                boardRepository
                        .findByName("default_board")
                        .orElseGet(() -> boardRepository.save(new Board("default_board")));

        // 에피소드에 기본 게시판 할당
        episode.assignDefaultBoard(defaultBoard);
    }

    private Pageable getPageable(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page - 1, size, sort);
    }
}
