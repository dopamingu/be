package com.dopamingu.be.domain.episode.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dopamingu.be.domain.board.domain.Board;
import com.dopamingu.be.domain.board.repository.BoardRepository;
import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.episode.domain.EpisodeTheme;
import com.dopamingu.be.domain.episode.repository.EpisodeRepository;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final BoardRepository boardRepository;

    public EpisodeService(EpisodeRepository episodeRepository, BoardRepository boardRepository) {
        this.episodeRepository = episodeRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Episode createEpisode(EpisodeTheme episodeTheme, String title, String content) {
        // 기본 게시판 검색 또는 생성
        Board defaultBoard = boardRepository.findByName("default_board")
                .orElseGet(() -> boardRepository.save(new Board("default_board")));

        // 에피소드 생성 및 기본 게시판 할당
        Episode episode = Episode.builder()
                .title(title)
                .content(content)
                .build();
        episode.assignDefaultBoard(defaultBoard);

        return episodeRepository.save(episode);
    }
}
