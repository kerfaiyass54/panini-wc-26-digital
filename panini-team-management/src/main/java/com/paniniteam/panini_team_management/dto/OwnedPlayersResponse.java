package com.paniniteam.panini_team_management.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OwnedPlayersResponse {

    private String email;

    private List<PlayerResponse> players;
}