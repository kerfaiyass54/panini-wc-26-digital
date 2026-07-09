package com.paniniteam.panini_team_management.dto;

import com.paniniteam.panini_team_management.enums.Tactic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeamCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotNull
    private Tactic tactic;

    @NotEmpty
    private List<String> playerCodes;

    @NotBlank
    private String goalkeeperId;
}