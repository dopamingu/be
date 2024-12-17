package com.dopamingu.be.domain.board.repository;

import com.dopamingu.be.domain.board.domain.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByName(String name);
}
