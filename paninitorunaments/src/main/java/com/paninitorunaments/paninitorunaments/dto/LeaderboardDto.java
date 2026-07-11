package com.paninitorunaments.paninitorunaments.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderboardDto {

    private String email;

    private Integer tournamentsWon;

    private Integer matchesWon;

    private Integer goalsScored;

    private Integer score;
}