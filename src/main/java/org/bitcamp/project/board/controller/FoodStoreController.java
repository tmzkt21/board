package org.bitcamp.project.board.controller;

import lombok.extern.log4j.Log4j2;
import org.bitcamp.project.board.common.dto.FoodStoreDTO;
import org.bitcamp.project.board.common.dto.ListFoodStoreDTO;
import org.bitcamp.project.board.common.service.FoodStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@Log4j2
public class FoodStoreController {

    private  FoodStoreService foodStoreService;

    @PostMapping("/")
    public ResponseEntity<Long> register(@RequestBody FoodStoreDTO dto){

        log.info(dto);

        Long fno = foodStoreService.register(dto);

        return ResponseEntity.ok(fno);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ListFoodStoreDTO>> getList(){

        return ResponseEntity.ok(foodStoreService.getList());
    }

}