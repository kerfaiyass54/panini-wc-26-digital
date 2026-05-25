package com.wcpanini.demo.controllers;

import com.wcpanini.demo.services.ContinentsConsumerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/continents")
@CrossOrigin("*")
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
    getAllContinents(
            @RequestParam String email
    ) {

        return ResponseEntity.ok(
                continentsConsumerService
                        .getContinentsData(email)
        );
    }

    // GET SINGLE CONTINENT

    @GetMapping("/{continent}")
    public ResponseEntity<Integer>
    getContinentCount(

            @RequestParam String email,

            @PathVariable String continent
    ) {

        Integer count =
                continentsConsumerService
                        .getContinentCount(
                                email,
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