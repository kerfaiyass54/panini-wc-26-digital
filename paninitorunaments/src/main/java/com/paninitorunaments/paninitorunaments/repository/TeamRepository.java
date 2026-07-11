package com.paninitorunaments.paninitorunaments.repository;


import com.paninitorunaments.paninitorunaments.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByEmail(
            String email
    );
}