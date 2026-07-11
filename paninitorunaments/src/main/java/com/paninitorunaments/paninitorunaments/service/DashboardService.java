package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.dto.DashboardResponse;

public interface DashboardService {

    DashboardResponse getDashboard(
            String email
    );
}