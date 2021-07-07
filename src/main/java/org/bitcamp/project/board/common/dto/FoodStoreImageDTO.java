package org.bitcamp.project.board.common.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodStoreImageDTO {

    private String uuid;

    private String fileName;

    private boolean main;
}
