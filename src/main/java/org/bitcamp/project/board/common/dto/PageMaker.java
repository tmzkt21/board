package org.bitcamp.project.board.common.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//현재 페이지 번호, 사이즈, 전체 개수 있어야함
@Data
public class PageMaker {

    private int page;
    private int size;
    private int totalCount;
    private List<Integer> pageList;
    private boolean prev,next;

    public PageMaker(int page, int size, int totalCount){
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;

        int totalPage = (int)(Math.ceil(totalCount/(double)size));
        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        int start = tempEnd - 9;

        prev = start > 1;

        int end = totalPage > tempEnd ? tempEnd: totalPage; //진짜 마지막!

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
