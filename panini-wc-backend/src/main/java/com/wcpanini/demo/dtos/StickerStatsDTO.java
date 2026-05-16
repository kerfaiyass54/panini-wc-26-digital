package com.wcpanini.demo.dtos;

public record StickerStatsDTO(
        long total,
        long logos,
        long intros,
        long players
) {}