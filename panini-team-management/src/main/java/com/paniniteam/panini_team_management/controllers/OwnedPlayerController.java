package com.paniniteam.panini_team_management.controllers;

import com.paniniteam.panini_team_management.dto.OwnedPlayersResponse;
import com.paniniteam.panini_team_management.dto.PositionStatsResponse;
import com.paniniteam.panini_team_management.services.OwnedPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owned-players")
@RequiredArgsConstructor
public class OwnedPlayerController {

    private final OwnedPlayerService ownedPlayerService;

    @GetMapping("/{email}")
    public ResponseEntity<OwnedPlayersResponse> getOwnedPlayers(
            @PathVariable String email) {

        return ResponseEntity.ok(
                ownedPlayerService.getOwnedPlayers(email)
        );
    }

    @GetMapping("/{email}/positions")
    public ResponseEntity<PositionStatsResponse> getPositionStats(
            @PathVariable String email) {

        return ResponseEntity.ok(
                ownedPlayerService.getPositionStats(email)
        );
    }
}