package org.bitcamp.project.board.common.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListFoodStoreDTO {

    private Long fno;
    private String fname;
    private String uuid;
    private String fileName;

    public String getLink() {
        return uuid+"_"+fileName;
    }
    public String getThumb(){
        return "s_"+ getLink();
    }
}