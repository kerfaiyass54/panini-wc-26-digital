package com.wcpanini.demo.controllers;

import com.wcpanini.demo.services.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collection")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping("/generate")
    public ResponseEntity<Void> generateTeam(
            @RequestParam String email
    ) {

        collectionService.generateTeam(email);

        return ResponseEntity.accepted()
                .build();
    }
}