package com.wcpanini.demo.controllers;

import com.wcpanini.demo.dtos.OwnedPlayerDTO;
import com.wcpanini.demo.services.OwnedPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/owned-players")
@RequiredArgsConstructor
public class OwnedPlayersController {

    private final OwnedPlayerService ownedPlayerService;

    @GetMapping("/{email}")
    public ResponseEntity<List<OwnedPlayerDTO>> getOwnedPlayers(
            @PathVariable String email
    ) {
        return ResponseEntity.ok(ownedPlayerService.getOwnedPlayers(email));
    }
}
