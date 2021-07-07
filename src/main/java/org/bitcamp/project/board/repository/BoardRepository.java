package org.bitcamp.project.board.repository;

import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.repository.dynamic.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    @Query("select b from Board b where b.content like concat('%',:keyword,'%') order by b.bno")
    Page<Board> getList(String keyword, Pageable pageable);
}
