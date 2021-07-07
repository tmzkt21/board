package org.bitcamp.project.board.entity;

import lombok.*;
import org.bitcamp.project.board.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
@ToString
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String content;
    private String writer;

    public void changeTile(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }
    public void changeWriter(String writer){
        this.writer = writer;
    }

}
