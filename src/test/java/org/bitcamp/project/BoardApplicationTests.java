package org.bitcamp.project;

import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.BoardImage;
import org.bitcamp.project.board.entity.Reply;
import org.bitcamp.project.board.repository.BoardRepository;
import org.bitcamp.project.board.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
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


    // 게시물 200개 만들기
    @Test
    public void boardInsert() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            boardRepository.save(Board.builder()
                    .title("게시물제목" + i)
                    .content("게시물내용" + i)
                    .writer("작성자" + i)
                    .build());
        });
    }

    // 게시물마다 댓글 만들기
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
        // Optional = null이 될 수도 있는 객체”을 감싸고 있는 일종의 래퍼 클래스
        Optional<Reply> reply = replyRepository.findById(36L);
            log.info(reply+"reply");
        reply.ifPresent(todo -> {
            log.info(todo + "ifPresent reply");
        });
    }

    // 지우기
    @Test
    public void replyDelete() {
        replyRepository.deleteById(26L);
    }

    // 업데이트 엔티티방식 제목변경 80
    @Test
    public void replyUpdate() {
        Optional<Reply> result = replyRepository.findById(80L);
        result.ifPresent(todo -> {
            todo.changeReplyText("댓글변경");
            replyRepository.save(todo);
            log.info(todo);
        });
    }

    // 레파지토리 방식
    @Transactional
    @Commit
    @Test
    public void replyUpdate2() {
        replyRepository.change("댓글수정", 196L);
    }

    //레파지토리 쿼리 방식 서치 content 번호로 찾고 페이지로 뽑아내기
    @Test
    public void testSearch() {
        String keyword = "85";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> page = boardRepository.getList(keyword, pageable);
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
        Pageable pageable = PageRequest.of(0, 10);
        Page<Board> page = boardRepository.getSearch(keyword, pageable);
        page.getContent().forEach(todo -> {
            log.info(todo);
        });
    }

    // 새로운 타이틀하나를 만들고 파일2개를 추가해라
    @Test
    public void imageInsert() {
        Board board = Board.builder().title("새로운제목").build();

        board.addImage(BoardImage.builder().uuid("파일아이디4").build());
        board.addImage(BoardImage.builder().uuid("파일아이디3").build());
        // board title 가입인사 가 새로생겼고 그 bno 번호에 맞춰서
        // 중간 페이블에서 bno 번호 와  그에맞는 이미지이름이 2개가 생긴것을 볼수있다
        // 게시판하나에 파일이 여러개일경우..
        // 보드이미지 엔티티를 가져올때 oneToMany 그리고 set으로 가져왔기 때문에 add 로 값을 집어넣어주고있다
        boardRepository.save(board);
    }

    //  read test
    @Test
    @Transactional
    // 1002 번째 번호로 찾아서 제목과 파일만 뽑아내봐라
    public void testRegister() {

        Optional<Board> result = boardRepository.findById(1002L);

        if (result.isPresent()) {
            Board board = result.get();
            log.info(board.getTitle());
            log.info(board.getBoardImages());
        }


        // if result.isPresent() get 후 엔티티에 다시담아야한다
        // 저런방식을 쓰지않고 ifpreesnt 람다로 만 돌리면 title은 확인되지만 이미지가 정상적으로 출력되지않는다

    }


}
