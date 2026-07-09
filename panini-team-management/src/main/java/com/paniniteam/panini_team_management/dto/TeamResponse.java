package com.paniniteam.panini_team_management.dto;

import com.paniniteam.panini_team_management.enums.Tactic;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TeamResponse {

    private String id;

    private String name;

    private String email;

    private Tactic tactic;

    private String goalkeeperId;

    private List<PlayerResponse> players;
}