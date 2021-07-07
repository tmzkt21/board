package org.bitcamp.project.board.common.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class FoodStoreImage {

    @Id
    private String uuid;

    private String fileName;

    private boolean main;
}