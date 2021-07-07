package org.bitcamp.project.board.entity;

import lombok.*;
import org.bitcamp.project.board.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reply", indexes = @Index(name = "board", columnList = "board_bno"))
@Builder
@ToString(exclude = "board")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String replyText;

    @ManyToOne(fetch = FetchType.LAZY)
    Board board;

    public void changeReplyText(String replyText) {
        this.replyText = replyText;
    }

}
