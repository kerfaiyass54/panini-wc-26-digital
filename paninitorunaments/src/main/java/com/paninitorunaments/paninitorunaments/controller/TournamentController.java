package com.paninitorunaments.paninitorunaments.controller;



import com.paninitorunaments.paninitorunaments.dto.*;
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

    @GetMapping("/{id}/next-journey")
    public ResponseEntity<NextJourneyResponse> getNextJourney(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                new NextJourneyResponse(
                        tournamentService.getNextJourney(id)
                )
        );
    }

    @PostMapping("/{id}/journey/{journey}/play")
    public ResponseEntity<Void> playJourney(
            @PathVariable Long id,
            @PathVariable Integer journey
    ) {

        tournamentService.playJourney(
                id,
                journey
        );

        return ResponseEntity.accepted()
                .build();
    }

    @GetMapping("/{id}/journey/{journey}")
    public ResponseEntity<List<Match>> getJourneyMatches(
            @PathVariable Long id,
            @PathVariable Integer journey
    ) {

        Championnat championnat =
                tournamentService.getTournament(id);

        List<Match> matches =
                championnat.getMatches()
                        .stream()
                        .filter(
                                match ->
                                        match.getJourney()
                                                .equals(journey)
                        )
                        .toList();

        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<TournamentStatusResponse>
    getStatus(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.getStatus(id)
        );
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<TournamentStatisticsResponse>
    getStatistics(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.getStatistics(id)
        );
    }

    @GetMapping("/{id}/journeys")
    public ResponseEntity<List<JourneyDto>>
    getJourneys(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.getJourneys(id)
        );
    }




    @GetMapping("/{id}/top-scorers")
    public ResponseEntity<List<TopScorerResponse>>
    getTopScorers(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService
                        .getTournamentTopScorers(id)
        );
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<MatchResultDto>>
    getResults(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                tournamentService.getResults(id)
        );
    }

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
                                request.getTournament(),
                                request.getEmail()
                        )
                );
    }



    @GetMapping("/email/{email}")
    public ResponseEntity<List<Championnat>>
    getByEmail(
            @PathVariable String email
    ) {

        return ResponseEntity.ok(
                tournamentService.getByEmail(email)
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