package com.paninitorunaments.paninitorunaments.controller;

import com.paninitorunaments.paninitorunaments.dto.MatchResultRequest;
import com.paninitorunaments.paninitorunaments.dto.TopScorerResponse;
import com.paninitorunaments.paninitorunaments.entity.Goal;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/{id}/result")
    public ResponseEntity<Match> saveResult(
            @PathVariable Long id,
            @RequestBody MatchResultRequest request
    ) {

        return ResponseEntity.ok(
                matchService.saveResult(id, request)
        );
    }

    @PostMapping("/{id}/play")
    public ResponseEntity<Void> playMatch(
            @PathVariable Long id
    ) {

        matchService.startMatch(id);

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatch(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                matchService.getMatch(id)
        );
    }

    @GetMapping("/{id}/goals")
    public ResponseEntity<List<Goal>> getGoals(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                matchService.getMatchGoals(id)
        );
    }

    @GetMapping("/top-scorers")
    public ResponseEntity<List<TopScorerResponse>> getTopScorers() {

        return ResponseEntity.ok(
                matchService.getTopScorers()
        );
    }
}