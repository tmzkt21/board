package org.bitcamp.project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListBoardDTO {

    private Long bno;
    private String title;
    private String content;
    private String writer;
    private String uuid;
    private String fileName;

}
