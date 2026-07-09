package com.paniniteam.panini_team_management.dto;

import com.paniniteam.panini_team_management.enums.Position;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class PositionStatsResponse {

    private Map<Position, Long> positions;
}