package org.bitcamp.project.board.repository;

import org.bitcamp.project.board.dto.BoardDTO;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.repository.dynamic.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//getBoardList 쿼리
// 보드에서  bno title content writer 그리고 보드 이미지에서 uuid fileName 을 가져와서
//보드에 보드이미지를 보드에 합친다 left join
// 보드이미지에 메인이 트루면서 보드에 bno 값이 0보다 크면  그룹바이 b
public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    @Query("select b from Board b where b.content like concat('%',:keyword,'%') order by b.bno")
    Page<Board> getList(String keyword, Pageable pageable);

    @Query("select b.bno, b.title ,b.content ,b.writer ,bi.uuid ,bi.fileName " +
            "from Board b left join b.boardImages bi " +
            "where bi.main=true and b.bno > 0 group by b")
    Page<Object[]> getBoardList(Pageable pageable);

    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

//    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
//    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    //SELECT column_name(s)
    //FROM table1
    //LEFT JOIN table2
    //ON table1.column_name=table2.column_name;
    //
}
