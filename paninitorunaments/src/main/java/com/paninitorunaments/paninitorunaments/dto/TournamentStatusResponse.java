package com.paninitorunaments.paninitorunaments.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentStatusResponse {

    private Long tournamentId;

    private String tournamentName;

    private Integer totalMatches;

    private Integer playedMatches;

    private Integer remainingMatches;

    private Integer currentJourney;

    private Boolean finished;

    private String champion;
}