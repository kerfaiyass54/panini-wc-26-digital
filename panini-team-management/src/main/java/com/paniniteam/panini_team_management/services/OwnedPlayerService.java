package com.paniniteam.panini_team_management.services;


import com.paniniteam.panini_team_management.dto.OwnedPlayersResponse;
import com.paniniteam.panini_team_management.dto.PlayerResponse;
import com.paniniteam.panini_team_management.dto.PositionStatsResponse;
import com.paniniteam.panini_team_management.entities.OwnedPlayer;
import com.paniniteam.panini_team_management.entities.Player;
import com.paniniteam.panini_team_management.enums.Position;
import com.paniniteam.panini_team_management.repositories.OwnedPlayerRepository;
import com.paniniteam.panini_team_management.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnedPlayerService {

    private final OwnedPlayerRepository ownedRepo;
    private final PlayerRepository playerRepo;

    private PlayerResponse toDto(Player player) {

        return PlayerResponse.builder()
                .code(player.getCode())
                .name(player.getName())
                .position(player.getPosition())
                .ability(player.getAbility())
                .build();
    }

    public OwnedPlayersResponse getOwnedPlayers(String email) {

        OwnedPlayer owned = ownedRepo.findByEmail(email)
                .orElseThrow();

        List<PlayerResponse> players =
                playerRepo.findByCodeIn(owned.getCodes())
                        .stream()
                        .map(this::toDto)
                        .toList();

        return OwnedPlayersResponse.builder()
                .email(email)
                .players(players)
                .build();
    }

    public PositionStatsResponse getPositionStats(String email) {

        OwnedPlayer owned = ownedRepo.findByEmail(email)
                .orElseThrow();

        Map<Position, Long> stats =
                playerRepo.findByCodeIn(owned.getCodes())
                        .stream()
                        .collect(Collectors.groupingBy(
                                Player::getPosition,
                                Collectors.counting()));

        return PositionStatsResponse.builder()
                .positions(stats)
                .build();
    }
}
