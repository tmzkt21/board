package org.bitcamp.project.board.entity;

import lombok.*;
import org.bitcamp.project.board.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BoardImage> boardImages = new HashSet<>();

    //cascade 영속성 전이 즉 부모가 삭제되면 자식도 삭제 all 은 전부
    //게시판이 삭제되면 그 이미지도 파일도 삭제한다는 뜻 set으로 넣어야한다

    public void changeTile(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }
    public void changeWriter(String writer){
        this.writer = writer;
    }
    public void addImage(BoardImage image) {
        boardImages.add(image);
    }

    public void removeImage(String uuid) {
        boardImages = boardImages.stream()
                .filter(bi -> bi.getUuid().equals(uuid) == false)
                //새로 들어온 이미지 넘버와 안에있는 uuid를 비교해서 틀리면 toset 새로 집어넣는다..?
                .collect(Collectors.toSet());
    }
}
