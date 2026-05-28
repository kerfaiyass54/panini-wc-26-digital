package com.wcpanini.demo.controllers;


import com.wcpanini.demo.dtos.ItemDto;
import com.wcpanini.demo.services.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> save(@RequestBody ItemDto dto) {

        ItemDto response = itemService.save(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}