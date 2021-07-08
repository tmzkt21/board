package org.bitcamp.project.board.repository;

import org.bitcamp.project.board.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<BoardImage,String> {
}
