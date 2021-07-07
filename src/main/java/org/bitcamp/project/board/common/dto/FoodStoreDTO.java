package org.bitcamp.project.board.common.dto;

import lombok.*;
import org.bitcamp.project.board.common.entity.FoodStoreImage;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodStoreDTO {

    private Long fno;

    private String fname;

    private List<FoodStoreImage> imageList;
}