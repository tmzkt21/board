package org.bitcamp.project.board.service;

import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.dto.ReplyDTO;
import org.bitcamp.project.board.dto.UploadResultDTO;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.BoardImage;
import org.bitcamp.project.board.entity.Reply;

import java.util.List;
import java.util.Set;

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
    default ReplyDTO replyEntityToDTO(Reply reply) {
        return ReplyDTO.builder()
                .replyText(reply.getReplyText())
                .board(reply.getBoard())
                .build();
    }



    //        default FoodStore dtoToEntity(FoodStoreDTO storeDTO){
//
//            Set<FoodStoreImage> imageSet = storeDTO.getImageList().stream()
//                    .map(imageDTO -> FoodStoreImage.builder()
//                            .uuid(imageDTO.getUuid())
//                            .fileName(imageDTO.getFileName())
//                            .main(imageDTO.isMain())
//                            .build())
//                    .collect(Collectors.toSet());
//
//            return FoodStore.builder()
//                    .fno(storeDTO.getFno())
//                    .fname(storeDTO.getFname())
//                    .storeImages(imageSet)
//                    .build();
//        }


    BoardDTO read(Long bno);

    Long delete(Long bno);

    BoardDTO update(BoardDTO boardDTO);

    ListResponseDTO<BoardDTO> list(ListRequestDTO listRequestDTO);


    void replyUpdate(Long bno,ReplyDTO replyDTO);
}
