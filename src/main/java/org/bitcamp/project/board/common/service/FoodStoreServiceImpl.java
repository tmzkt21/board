package org.bitcamp.project.board.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.common.dto.FoodStoreDTO;
import org.bitcamp.project.board.common.dto.ListFoodStoreDTO;
import org.bitcamp.project.board.common.entity.FoodStore;
import org.bitcamp.project.board.common.repository.FoodStoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class FoodStoreServiceImpl implements FoodStoreService{

    private final FoodStoreRepository foodStoreRepository;

    @Override
    public Long register(FoodStoreDTO storeDTO) {

        FoodStore entity = dtoToEntity(storeDTO);

        FoodStore result = foodStoreRepository.save(entity);

        return result.getFno();
    }

    @Override
    public List<ListFoodStoreDTO> getList() {

        Pageable pageable =
                PageRequest.of(0,10, Sort.by("fno").descending());

        Page<Object[]> result = foodStoreRepository.getList(pageable);

        return result.getContent().stream().map(arr -> arrToDTO(arr)).collect(Collectors.toList());

    }
}