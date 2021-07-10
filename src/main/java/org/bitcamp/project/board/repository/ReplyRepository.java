package org.bitcamp.project.board.repository;

import org.bitcamp.project.board.entity.Reply;
import org.bitcamp.project.board.repository.dynamic.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply,Long>, BoardSearch {



    @Modifying
    @Query("update Reply set replyText=:replyText where rno=:rno")
    void change(String replyText, long rno);


    @Query("select board from Reply where board=:bno")
    void readReplyByBoard(Long bno);

    @Transactional
    //UPDATE, DELETE 경우 @Transactional 어 로테이션을 추가해주지 않으면 에러발생
    @Modifying
    @Query("delete from Reply r where r.board.bno=:bno")
    void deleteByBno(Long bno);
}
