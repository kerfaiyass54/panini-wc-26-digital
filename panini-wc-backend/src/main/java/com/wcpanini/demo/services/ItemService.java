package com.wcpanini.demo.services;




import com.wcpanini.demo.dtos.ItemDto;
import com.wcpanini.demo.entities.Item;
import com.wcpanini.demo.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemDto save(ItemDto dto) {

        Item document = Item.builder()
                .id(UUID.randomUUID().toString())
                .code(dto.getCode())
                .name(dto.getName())
                .createdAt(LocalDateTime.now())
                .build();

        Item saved = itemRepository.save(document);

        return ItemDto.builder()
                .code(saved.getCode())
                .name(saved.getName())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}