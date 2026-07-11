package com.paninitorunaments.paninitorunaments.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamRankingDto {

    private Long id;

    private String name;

    private String email;

    private Double averageAbility;

    private Integer playersCount;
}