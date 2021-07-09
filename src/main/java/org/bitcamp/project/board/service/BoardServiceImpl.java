package org.bitcamp.project.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.common.dto.PageMaker;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.dto.BoardImageDTO;
import org.bitcamp.project.board.dto.ListBoardDTO;
import org.bitcamp.project.board.dto.ReplyDTO;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.BoardImage;
import org.bitcamp.project.board.entity.Reply;
import org.bitcamp.project.board.repository.BoardImageRepository;
import org.bitcamp.project.board.repository.BoardRepository;
import org.bitcamp.project.board.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final BoardImageRepository boardImageRepository;


    // dto 값을 엔티티 바꿔야한다..
    @Override
    public Long create(BoardDTO boardDTO) {

        Board board = dtoToEntity(boardDTO);

        Board save = boardRepository.save(board);

        return save.getBno();
    }


    @Override
    public Long delete(Long bno) {

        boardRepository.deleteById(bno);

        return null;
    }

    @Override
    public BoardDTO update(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        if (result.isPresent()) {
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

        Page<Board> result = boardRepository.getSearch(dto.getKeyword(), dto.getPageable());

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(todo -> entityToDTO(todo))
                .collect(Collectors.toList());

        PageMaker pageMaker = new PageMaker(dto.getPage(), dto.getSize(), (int) result.getTotalElements());


        return ListResponseDTO.<BoardDTO>builder()
                .dtoList(dtoList)
                .pageMaker(pageMaker)
                .listRequestDTO(dto)
                .build();
    }

    @Override
    public BoardDTO read(Long bno) {
        log.info("서비스임플리드들어옴");
        Optional<Board> result = boardRepository.findById(bno);

        if (result.isPresent()) {
            Board board = result.get();
            replyRepository.readReplyByBoard(board.getBno());
            log.info("리플라이쿼리문");
            return entityToDTO(board);
        }
        log.info("이프문끝");
        return null;
    }

    @Override
    public void replyUpdate(Long bno, ReplyDTO replyDTO) {
        Optional<Board> board = boardRepository.findById(bno);
        log.info(board + "8번게시물데이터");
        // 8 게시물데이터
        board.ifPresent(todo -> {
            Reply reply = Reply.builder()
                    .replyText(replyDTO.getReplyText())
                    .board(todo)
                    .build();
            replyRepository.save(reply);
            log.info(reply + "리플라이 새이브한값");
        });

    }


    @Override
    public List<ListBoardDTO> getList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardList(pageable);
        // 엔티티result 디티오
        return result.getContent().stream()
                .map(arr -> arrToDTO(arr)).collect(Collectors.toList());
    }


//    @Override
//    public Long fileSave(Long bno, List<BoardImageDTO> result) {
//        log.info(result + "임플넘어옴");
//        Set<BoardImage> image = imageDtoToEntity(result);
//        log.info(image + "엔티티변환 이미지");
//        Board board = Board.builder().bno(bno).boardImages(image).build();
//        boardRepository.save(board);
//        log.info(image + "저장되는 이미지값");
//        return null;
//    }

    @Override
    public Long boardRegister(BoardDTO dto) {
        Board entity = dtoToEntity(dto);

        Board result = boardRepository.save(entity);

        return result.getBno();
    }


}
