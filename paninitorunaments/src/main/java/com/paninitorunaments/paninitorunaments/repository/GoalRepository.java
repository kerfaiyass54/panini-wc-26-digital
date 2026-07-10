package com.paninitorunaments.paninitorunaments.repository;

import com.paninitorunaments.paninitorunaments.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GoalRepository
        extends JpaRepository<Goal, Long> {

    @Query("""
SELECT g.player.name, COUNT(g)
FROM Goal g
GROUP BY g.player.name
ORDER BY COUNT(g) DESC
""")
    List<Object[]> getTopScorers();

    List<Goal> findByMatchId(Long matchId);

}