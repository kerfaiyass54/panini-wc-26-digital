package com.paninitorunaments.paninitorunaments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchRequest {

    private Long matchId;

    private Long tournamentId;

    private String homeTeam;

    private String awayTeam;

    List<PlayerDto> homePlayers;
    List<PlayerDto> awayPlayers;
    Double homeMorale;
    Double awayMorale;
}