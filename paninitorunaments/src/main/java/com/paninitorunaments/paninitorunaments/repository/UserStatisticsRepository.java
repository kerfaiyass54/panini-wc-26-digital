package com.paninitorunaments.paninitorunaments.repository;


import com.paninitorunaments.paninitorunaments.entity.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatisticsRepository
        extends JpaRepository<UserStatistics, Long> {

    Optional<UserStatistics> findByEmail(
            String email
    );
}