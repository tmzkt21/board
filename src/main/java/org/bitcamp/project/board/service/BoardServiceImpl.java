package org.bitcamp.project.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.common.dto.PageMaker;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.dto.ReplyDTO;
import org.bitcamp.project.board.dto.UploadResultDTO;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.BoardImage;
import org.bitcamp.project.board.entity.Reply;
import org.bitcamp.project.board.repository.BoardRepository;
import org.bitcamp.project.board.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;



    // dto 값을 엔티티 바꿔야한다..
    @Override
    public Long create(BoardDTO boardDTO) {

        Board board = dtoToEntity(boardDTO);

       Board save =  boardRepository.save(board);

        return save.getBno();
    }

    @Override
    public BoardDTO read(Long bno) {

     Optional<Board> result = boardRepository.findById(bno);

     if(result.isPresent()) {
         Board board = result.get();
         return entityToDTO(board);
     }


        return null;
    }

    @Override
    public Long delete(Long bno) {

        boardRepository.deleteById(bno);

        return null;
    }

    @Override
    public BoardDTO update(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        if (result.isPresent()){
            Board entity = result.get();
            entity.changeTile(boardDTO.getTitle());
            entity.changeContent(boardDTO.getContent());
            entity.changeWriter(boardDTO.getWriter());
            boardRepository.save(entity);
            return entityToDTO(entity);
        }
        return null;
    }

    @Override
    public ListResponseDTO<BoardDTO> list(ListRequestDTO dto) {

        Page<Board> result = boardRepository.getSearch(dto.getKeyword(),dto.getPageable());

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(todo -> entityToDTO(todo))
                .collect(Collectors.toList());

        PageMaker pageMaker = new PageMaker(dto.getPage(),dto.getSize(),(int)result.getTotalElements());



        return ListResponseDTO.<BoardDTO>builder()
                .dtoList(dtoList)
                .pageMaker(pageMaker)
                .listRequestDTO(dto)
                .build();
    }

    @Override
    public void replyUpdate(Long bno, ReplyDTO replyDTO) {
        Optional<Board> board = boardRepository.findById(bno);
        log.info(board +"8번게시물데이터");
        // 8 게시물데이터
        board.ifPresent(todo-> {
           Reply reply = Reply.builder()
                    .replyText(replyDTO.getReplyText())
                    .board(todo)
                    .build();
            replyRepository.save(reply);
            log.info(reply+"리플라이 새이브한값");
        });

     }


}
