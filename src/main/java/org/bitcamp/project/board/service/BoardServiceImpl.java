package org.bitcamp.project.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.common.dto.PageMaker;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.dto.ListBoardDTO;
import org.bitcamp.project.board.dto.ReplyDTO;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.Reply;
import org.bitcamp.project.board.repository.BoardImageRepository;
import org.bitcamp.project.board.repository.BoardRepository;
import org.bitcamp.project.board.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        replyRepository.deleteByBno(bno);
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
        Optional<Board> result = boardRepository.findById(bno);

        if (result.isPresent()) {
            Board board = result.get();
            return entityToDTO(board);
        }
        return null;
    }

    @Override
    public Optional<Board> replyCreate(Long bno, ReplyDTO replyDTO) {
        Optional<Board> result = boardRepository.findById(bno);
        log.info(result + "8번게시물데이터");
        // 8 게시물데이터
         result.ifPresent(todo -> {
            Reply reply = Reply.builder()
                    .replyText(replyDTO.getReplyText())
                    .board(todo)
                    .build();
            replyRepository.save(reply);
            log.info(reply + "리플라이 새이브한값");
        });
        log.info(result+"리절트값");
        return result;
    }


    @Override
    public List<ListBoardDTO> getList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardList(pageable);
        // 엔티티result 디티오
        log.info(result + "결과");
        return result.getContent().stream()
                .map(arr -> arrToDTO(arr)).collect(Collectors.toList());
    }

    @Override
    public List<Object[]> boardReply(Long bno) {
        List<Object[]> board = boardRepository.getBoardWithReply(bno);
        for (Object[] arr : board) {
            Arrays.toString(arr);
            log.info(Arrays.toString(arr));
        }

        return board;
    }

    @Override
    public ReplyDTO replyUpdate(Long rno, ReplyDTO replyDTO) {
        Optional<Reply> reply = replyRepository.findById(rno);
        reply.ifPresent(todo -> {
            todo.changeReplyText(replyDTO.getReplyText());
            replyRepository.save(todo);

        });
        return replyDTO;
    }

    @Override
    public Long replyDelete(Long rno) {
        replyRepository.deleteById(rno);

        return rno;
    }


}
