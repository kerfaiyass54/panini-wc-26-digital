package com.paninitorunaments.paninitorunaments.controller;



import com.paninitorunaments.paninitorunaments.dto.AddTeamsRequest;
import com.paninitorunaments.paninitorunaments.dto.CreateTournamentRequest;
import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.entity.Standing;
import com.paninitorunaments.paninitorunaments.service.MatchService;
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

    private final MatchService matchService;

    @GetMapping("/{id}/matches")
    public ResponseEntity<List<Match>> getTournamentMatches(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                matchService.getTournamentMatches(id)
        );
    }

    @PostMapping
    public ResponseEntity<Championnat> createTournament(
            @RequestBody CreateTournamentRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        tournamentService.createTournament(
                                request.getTournament()
                        )
                );
    }

    @PostMapping("/{id}/teams")
    public ResponseEntity<Championnat> addTeams(
            @PathVariable Long id,
            @RequestBody AddTeamsRequest request
    ) {

        return ResponseEntity.ok(
                tournamentService.addTeams(
                        id,
                        request.getTeamIds()
                )
        );
    }

    @PostMapping("/{id}/generate-fixtures")
    public ResponseEntity<Championnat> generateFixtures(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.generateFixtures(id)
        );
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

    @PostMapping("/{id}/initialize-standings")
    public ResponseEntity<Championnat> initializeStandings(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.initializeStandings(id)
        );
    }

    @GetMapping("/{id}/standings")
    public ResponseEntity<List<Standing>> getStandings(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.getStandings(id)
        );
    }
}