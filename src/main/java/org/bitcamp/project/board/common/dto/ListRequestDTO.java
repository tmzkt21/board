package org.bitcamp.project.board.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data                       //Getter Setter에 자유로움 = 수정이 자유로움
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 페이지 DTO
// vp
public class ListRequestDTO {
    @Builder.Default
    // 값이 없으면 디폴트 값으로 디폴트는  = 1
    private int page = 1;
    @Builder.Default
    private int size = 10;


    private String keyword;

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
        // 3항연산자 페이지가 1보다 작으면
    }

    public void setSize(int size) {
        this.size = size <= 10 ? 10 : size;
    }

    public Pageable getPageable(){
        return PageRequest.of(this.page-1,this.size);
    }



}

