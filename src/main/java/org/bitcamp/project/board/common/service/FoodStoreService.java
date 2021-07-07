package org.bitcamp.project.board.common.service;

import org.bitcamp.project.board.common.dto.FoodStoreDTO;
import org.bitcamp.project.board.common.dto.ListFoodStoreDTO;
import org.bitcamp.project.board.common.entity.FoodStore;
import org.bitcamp.project.board.common.entity.FoodStoreImage;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface FoodStoreService {

    Long register(FoodStoreDTO storeDTO);

    List<ListFoodStoreDTO> getList();

    default ListFoodStoreDTO arrToDTO(Object[] arr){

        return ListFoodStoreDTO.builder()
                .fno((Long)arr[0])
                .fname((String)arr[1])
                .uuid((String)arr[2])
                .fileName((String)arr[3])
                .build();

    }

    default FoodStore dtoToEntity(FoodStoreDTO storeDTO){

        Set<FoodStoreImage> imageSet = storeDTO.getImageList().stream()
                .map(imageDTO -> FoodStoreImage.builder()
                        .uuid(imageDTO.getUuid())
                        .fileName(imageDTO.getFileName())
                        .main(imageDTO.isMain())
                        .build())
                .collect(Collectors.toSet());

        return FoodStore.builder()
                .fno(storeDTO.getFno())
                .fname(storeDTO.getFname())
                .storeImages(imageSet)
                .build();
    }
}