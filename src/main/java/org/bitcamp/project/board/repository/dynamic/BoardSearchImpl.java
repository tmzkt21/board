package org.bitcamp.project.board.repository.dynamic;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.QBoard;
import org.bitcamp.project.board.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Reply.class);
    }

    @Override
    public void doA() {

        QBoard board = QBoard.board;
        JPQLQuery query = from(board);

        // 174 이상인녀석들
        query.where(board.bno.gt(174L));
        // bno 기준 내림차순
        query.orderBy(board.bno.desc());
        // 시작페이지
        query.offset(0);
        // 페이지 사이즈
        query.limit(10);


        List<Board> list = query.fetch();
        long count = query.fetchCount();


        log.warn("리스트출력"+list);
        log.warn("총카운트"+count);

    }

    @Override
    public Page<Board> getSearch(String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery query = from(board);

        // null 빈공간 체크후 맞으면 content 에 값담기
        if (keyword != null && keyword.trim().length() != 0){
            query.where(board.content.contains(keyword));
        }
        query.where(board.bno.gt(0L));
        query.orderBy(board.bno.desc());
        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());


        List<Board> list = query.fetch();
        long count = query.fetchCount();
        log.warn("리스트출력"+list);
        log.warn("총카운트"+count);


        return new PageImpl<>(list,pageable,count);
    }


}
