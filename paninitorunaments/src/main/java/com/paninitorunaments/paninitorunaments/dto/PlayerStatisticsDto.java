package com.paninitorunaments.paninitorunaments.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerStatisticsDto {

    private String player;

    private Long goals;
}