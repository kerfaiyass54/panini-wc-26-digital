package com.wcpanini.demo.dtos;

public record StickerDTO(
        Long   id,
        String name,
        String type,
        String nationality,
        String place
) {}