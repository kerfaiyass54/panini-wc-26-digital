package com.paninitorunaments.paninitorunaments.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentStatisticsResponse {

    private Long tournamentId;

    private String tournamentName;

    private Integer totalMatches;

    private Integer playedMatches;

    private Integer totalGoals;

    private Double averageGoalsPerMatch;

    private String topScorer;

    private Long topScorerGoals;

    private String bestAttack;

    private Integer bestAttackGoals;

    private String bestDefense;

    private Integer bestDefenseGoalsConceded;

    private String highestScoringMatch;
}