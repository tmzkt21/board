package org.bitcamp.project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bitcamp.project.board.entity.Board;
import org.bitcamp.project.board.entity.BoardImage;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private String replyText;
    private List<BoardImage> boardImages;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
