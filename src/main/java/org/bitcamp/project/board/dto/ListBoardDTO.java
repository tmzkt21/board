package org.bitcamp.project.board.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListBoardDTO {

    private Long bno;
    private String title;
    private String content;
    private String writer;
    private String uuid;
    private String fileName;

}
