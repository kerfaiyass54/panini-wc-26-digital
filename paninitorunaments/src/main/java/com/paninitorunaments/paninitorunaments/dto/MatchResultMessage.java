package com.paninitorunaments.paninitorunaments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class MatchResultMessage {

    private Long matchId;

    private Integer goalsHome;

    private Integer goalsAway;

    private List<GoalScorerDto> scorers;
}