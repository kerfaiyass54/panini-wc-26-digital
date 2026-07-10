package com.paninitorunaments.paninitorunaments.repository;


import com.paninitorunaments.paninitorunaments.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}