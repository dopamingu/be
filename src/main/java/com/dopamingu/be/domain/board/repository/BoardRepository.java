package com.dopamingu.be.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dopamingu.be.domain.board.domain.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByName(String name);
}