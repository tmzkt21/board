package org.bitcamp.project.board.service;

import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.dto.BoardImageDTO;
import org.bitcamp.project.board.dto.ListBoardDTO;
import org.bitcamp.project.board.dto.ReplyDTO;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.BoardImage;
import org.bitcamp.project.board.entity.Reply;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    default ListBoardDTO arrToDTO(Object[] arr) {

        return ListBoardDTO.builder()
                .bno((long)arr[0])
                .title((String) arr[1])
                .content((String) arr[2])
                .writer((String) arr[3])
                .uuid((String) arr[4])
                .fileName((String) arr[5])
                .build();
    }

    default BoardImage imageDtoToEntity(List<BoardImageDTO> dto) {
        Set<BoardImage> imageSet = dto.stream()
                .map(imageDTO -> BoardImage.builder()
                        .uuid(imageDTO.getUuid())
                        .fileName(imageDTO.getFileName())
                        .build())
                .collect(Collectors.toSet());
        return null;
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

    List<ListBoardDTO> getList();

    Long fileSave(List<BoardImageDTO> result);

    Long boardRegister(BoardDTO dto);
}
