package com.paniniteam.panini_team_management.services;

import com.paniniteam.panini_team_management.dto.PlayerResponse;
import com.paniniteam.panini_team_management.dto.TeamCreateRequest;
import com.paniniteam.panini_team_management.dto.TeamResponse;
import com.paniniteam.panini_team_management.entities.OwnedPlayer;
import com.paniniteam.panini_team_management.entities.Player;
import com.paniniteam.panini_team_management.entities.Team;
import com.paniniteam.panini_team_management.repositories.OwnedPlayerRepository;
import com.paniniteam.panini_team_management.repositories.PlayerRepository;
import com.paniniteam.panini_team_management.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepo;
    private final PlayerRepository playerRepo;
    private final OwnedPlayerRepository ownedRepo;

    public TeamResponse createTeam(TeamCreateRequest request) {

        OwnedPlayer owned =
                ownedRepo.findByEmail(request.getEmail())
                        .orElseThrow();

        boolean allOwned =
                owned.getCodes()
                        .containsAll(request.getPlayerCodes());

        if (!allOwned) {
            throw new IllegalArgumentException(
                    "Player not owned");
        }

        Team team = Team.builder()
                .name(request.getName())
                .email(request.getEmail())
                .playerCodes(request.getPlayerCodes())
                .goalkeeperId(request.getGoalkeeperId())
                .tactic(request.getTactic())
                .build();

        Team saved = teamRepo.save(team);

        return map(saved);
    }

    public List<TeamResponse> getTeams(String email) {

        return teamRepo.findByEmail(email)
                .stream()
                .map(this::map)
                .toList();
    }

    private TeamResponse map(Team team) {

        List<PlayerResponse> players =
                playerRepo.findByCodeIn(team.getPlayerCodes())
                        .stream()
                        .map(this::toDto)
                        .toList();

        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .email(team.getEmail())
                .tactic(team.getTactic())
                .goalkeeperId(team.getGoalkeeperId())
                .players(players)
                .build();
    }

    private PlayerResponse toDto(Player player) {

        return PlayerResponse.builder()
                .code(player.getCode())
                .name(player.getName())
                .position(player.getPosition())
                .ability(player.getAbility())
                .build();
    }
}