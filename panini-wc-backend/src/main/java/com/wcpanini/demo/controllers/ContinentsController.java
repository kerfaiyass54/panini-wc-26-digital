package com.wcpanini.demo.controllers;

import com.wcpanini.demo.services.ContinentsConsumerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/continents")
public class ContinentsController {

    private final ContinentsConsumerService
            continentsConsumerService;

    public ContinentsController(
            ContinentsConsumerService continentsConsumerService
    ) {

        this.continentsConsumerService =
                continentsConsumerService;
    }

    // GET ALL CONTINENTS

    @GetMapping
    public ResponseEntity<Map<String, Integer>>
    getAllContinents() {

        return ResponseEntity.ok(
                continentsConsumerService
                        .getContinentsData()
        );
    }

    // GET SINGLE CONTINENT

    @GetMapping("/{continent}")
    public ResponseEntity<Integer>
    getContinentCount(
            @PathVariable String continent
    ) {

        Integer count =
                continentsConsumerService
                        .getContinentCount(
                                continent
                        );

        if (count == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(
                count
        );
    }
}