package org.bitcamp.project.board.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardImageDTO {

    private String uuid;
    private String fileName;
    private boolean main;
}
