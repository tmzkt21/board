package org.bitcamp.project.board.repository.dynamic;

import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
    Page<Board> getSearch(String keyword, Pageable pageable);

    void doA();
}
