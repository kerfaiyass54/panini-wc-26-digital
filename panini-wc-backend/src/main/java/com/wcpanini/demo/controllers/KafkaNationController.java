package com.wcpanini.demo.controllers;

import com.wcpanini.demo.services.KafkaNationConsumerService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class KafkaNationController {

    private final KafkaNationConsumerService service;

    public KafkaNationController(
            KafkaNationConsumerService service
    ) {

        this.service = service;
    }

    // GET ALL COUNTRIES

    @GetMapping
    public Map<String, Integer> getAll() {

        return service.getAllStats();
    }

    // GET ONE COUNTRY

    @GetMapping("/{country}")
    public Integer getCountry(
            @PathVariable String country
    ) {

        return service.getCountryCount(
                country
        );
    }
}