// StatisticsController.java
package com.wcpanini.demo.controllers;

import com.wcpanini.demo.dtos.StatisticsResponse;
import com.wcpanini.demo.services.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin("*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(
            StatisticsService statisticsService
    ) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<StatisticsResponse> getStatistics(
            @PathVariable String email
    ) {

        StatisticsResponse response =
                statisticsService.getStatistics(email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/finished/{email}")
    public ResponseEntity<List<String>> getFinishedCountries(@PathVariable String email){
        List<String> finished = statisticsService.getFinishedCoutnries(email);
        return ResponseEntity.ok(finished);
    }
}