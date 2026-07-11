package com.paninitorunaments.paninitorunaments.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private Long teams;

    private Long tournaments;

    private Integer tournamentsWon;

    private Integer matchesPlayed;

    private Integer matchesWon;

    private Integer goalsScored;
}