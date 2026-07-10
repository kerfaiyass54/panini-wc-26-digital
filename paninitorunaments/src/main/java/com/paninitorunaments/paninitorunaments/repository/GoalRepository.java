package com.paninitorunaments.paninitorunaments.repository;

import com.paninitorunaments.paninitorunaments.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository
        extends JpaRepository<Goal, Long> {
}