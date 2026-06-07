package com.wcpanini.demo.dtos;

public record DuplicateResponse(
        Long id,
        String code,
        Integer number,
        String createdAt,
        String name
) {
}