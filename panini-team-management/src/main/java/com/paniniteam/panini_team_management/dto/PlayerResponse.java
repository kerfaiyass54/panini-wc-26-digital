package com.paniniteam.panini_team_management.dto;

import com.paniniteam.panini_team_management.enums.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerResponse {

    private String code;

    private String name;

    private Position position;

    private Integer ability;
}