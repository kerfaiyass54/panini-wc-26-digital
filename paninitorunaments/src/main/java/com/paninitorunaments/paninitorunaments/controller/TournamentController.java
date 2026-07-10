package com.paninitorunaments.paninitorunaments.controller;



import com.paninitorunaments.paninitorunaments.dto.AddTeamsRequest;
import com.paninitorunaments.paninitorunaments.dto.CreateTournamentRequest;
import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Championnat> createTournament(
            @RequestBody CreateTournamentRequest request
    ) {

        Championnat tournoi =
                tournamentService.createTournament(request.getTournament());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tournoi);
    }

    @PostMapping("/{id}/teams")
    public ResponseEntity<Championnat> addTeams(
            @PathVariable Long id,
            @RequestBody AddTeamsRequest request
    ) {

        Championnat tournoi =
                tournamentService.addTeams(id, request.getTeamIds());

        return ResponseEntity.ok(tournoi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Championnat> getTournament(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.getTournament(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<Championnat>> getAllTournaments() {

        return ResponseEntity.ok(
                tournamentService.getAll()
        );
    }
}