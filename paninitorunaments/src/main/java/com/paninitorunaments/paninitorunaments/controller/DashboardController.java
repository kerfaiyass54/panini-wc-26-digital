package com.paninitorunaments.paninitorunaments.controller;

import com.paninitorunaments.paninitorunaments.dto.DashboardResponse;
import com.paninitorunaments.paninitorunaments.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{email}")
    public ResponseEntity<DashboardResponse>
    getDashboard(
            @PathVariable String email
    ) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(email)
        );
    }
}