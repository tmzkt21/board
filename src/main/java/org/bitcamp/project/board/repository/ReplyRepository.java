package org.bitcamp.project.board.repository;

import org.bitcamp.project.board.entity.Reply;
import org.bitcamp.project.board.repository.dynamic.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply,Long>, BoardSearch {



    @Modifying
    @Query("update Reply set replyText=:replyText where rno=:rno")
    void change(String replyText, long rno);


    @Query("select board from Reply where board=:bno")
    void readReplyByBoard(Long bno);
}
