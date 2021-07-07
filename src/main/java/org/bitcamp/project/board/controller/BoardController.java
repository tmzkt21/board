package org.bitcamp.project.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.common.dto.ListRequestDTO;
import org.bitcamp.project.board.common.dto.ListResponseDTO;
import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/board")
@RestController
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<ListResponseDTO<BoardDTO>> list(ListRequestDTO listRequestDTO) {

        return ResponseEntity.ok(boardService.list(listRequestDTO));
    }

    // create
    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody BoardDTO boardDTO) {

       Long bno =  boardService.create(boardDTO);

        return ResponseEntity.ok(bno);
    }

    // read 번호로 찾는경우 경로에 담으면댐
    @GetMapping("/{bno}")
    public ResponseEntity<BoardDTO> read(@PathVariable Long bno) {

       BoardDTO result =  boardService.read(bno);

        return ResponseEntity.ok().body(result);
    }

    // delete
    @DeleteMapping("/{bno}")
    public ResponseEntity<Long> delete(@PathVariable Long bno) {

        Long delete =  boardService.delete(bno);

        return ResponseEntity.ok().body(delete);
    }


    //update
    // {} 몇번째 있는걸 찾아서 바꿈
    // bno / 바꾸는값은 {}
    @PutMapping("/{bno}")
    public ResponseEntity<BoardDTO> update(@PathVariable Long bno , @RequestBody BoardDTO boardDTO) {
        boardDTO.setBno(bno);
        boardService.update(boardDTO);
        return ResponseEntity.ok(boardDTO);
    }

}
