package org.bitcamp.project.board.repository;

import org.bitcamp.project.board.entity.BoardImage;
import org.bitcamp.project.board.repository.dynamic.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long>, BoardSearch {


}
