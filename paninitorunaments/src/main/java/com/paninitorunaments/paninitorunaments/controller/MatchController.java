package com.paninitorunaments.paninitorunaments.controller;

import com.paninitorunaments.paninitorunaments.dto.MatchResultRequest;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                matchService.saveResult(
                        id,
                        request
                )
        );
    }

    @PostMapping("/{id}/play")
    public ResponseEntity<Void> playMatch(
            @PathVariable Long id
    ) {

        matchService.startMatch(id);

        return ResponseEntity.accepted().build();
    }
}