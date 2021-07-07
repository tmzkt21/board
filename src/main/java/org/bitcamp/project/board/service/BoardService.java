package org.bitcamp.project.board.service;

import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.entity.Board;

public interface BoardService {
    Long create(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        return Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
    }

    default BoardDTO entityToDTO(Board board) {
        return BoardDTO.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .modDate(board.getModDate())
                .regDate(board.getRegDate())
                .build();
    }

    BoardDTO read(Long bno);

    Long delete(Long bno);

    BoardDTO update(BoardDTO boardDTO);

    ListResponseDTO<BoardDTO> list(ListRequestDTO listRequestDTO);
}
