package org.bitcamp.project;

import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.common.repository.FoodStoreRepository;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.Reply;
import org.bitcamp.project.board.repository.BoardRepository;
import org.bitcamp.project.board.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
class BoardApplicationTests {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    private FoodStoreRepository storeRepository;

    // 게시물 1000개 만들기
    @Test
    public void boardInsert() {
        IntStream.rangeClosed(1, 1000).forEach(i -> {

            boardRepository.save(Board.builder()
                    .title("게시물제목" + i)
                    .content("게시물내용" + i)
                    .writer("작성자" + i)
                    .build());
        });
    }

    // 게시문마다 댓글 만들기
    @Test
    public void replyInsert() {
        IntStream.rangeClosed(1, 500).forEach(i -> {
            long bno = (int) (Math.random() * 200) + 1;
            Board board = Board.builder().bno(bno).build();
            replyRepository.save(Reply.builder()
                    .replyText("댓글" + i)
                    .board(board)
                    .build());
        });
    }

    // 36번째 rno 로 찾기
    @Test
    public void replyRead() {
        Optional<Reply> reply = replyRepository.findById(36L);
        log.info(reply);
        reply.ifPresent(todo -> {
            log.info(todo + "결과");
        });
    }

    // 지우기
    @Test
    public void replyDelete() {
        replyRepository.deleteById(26L);
    }

    // 업데이트 엔티티방식 제목변경
    @Test
    public void replyUpdate() {
        Optional<Reply> reply = replyRepository.findById(80L);
        reply.ifPresent(todo -> {
            todo.changeReplyText("댓글수정");
            replyRepository.save(todo);
        });
    }
    // 레파지토리 방식
    @Transactional
    @Commit
    @Test
    public void replyUpdate2() {
        replyRepository.change("댓글수정",196L);
    }

    //레파지토리 쿼리 방식 서치 content 번호로 찾고 페이지로 뽑아내기
    @Test
    public void testSearch() {
        String keyword = "85";
        Pageable pageable = PageRequest.of(0,10);
        Page<Board> page = boardRepository.getList(keyword,pageable);
        page.getContent().forEach(todo -> {
            log.info(todo);
        });

    }

    // jpql 테스트
    @Test
    public void testDoA() {

        boardRepository.doA();

    }

    // jpql 방식 서치
    @Test
    public void replySearch() {
        String keyword = "832";
        Pageable pageable = PageRequest.of(0,10);
        Page<Board> page = boardRepository.getSearch(keyword,pageable);
        page.getContent().forEach(todo -> {
            log.info(todo);
        });
    }


    @Test
    public void testList(){

        Pageable pageable =
                PageRequest.of(0,10, Sort.by("fno").descending());

        Page<Object[]> result = storeRepository.getList(pageable);

        result.getContent().forEach(arr -> System.out.println(Arrays.toString(arr)));

    }




}
