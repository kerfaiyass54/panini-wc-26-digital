package com.paninitorunaments.paninitorunaments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultDto {

    private Long id;

    private String homeTeam;

    private String awayTeam;

    private Integer goalsHome;

    private Integer goalsAway;

    private Integer journey;
}