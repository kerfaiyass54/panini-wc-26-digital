package com.wcpanini.demo.dtos;

public record OwnedPlayerDTO(
        Long id,
        String code,
        String name,
        String nationality,
        String position
) {
}
