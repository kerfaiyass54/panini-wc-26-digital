// OwningRepository.java
package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Owning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OwningRepository extends JpaRepository<Owning, Long> {

    boolean existsByEmailAndCode(String email, String code);

    Optional<Owning> findByEmailAndCode(String email, String code);

    Page<Owning> findAllByEmail(String email, Pageable pageable);

    Long countByEmail(String email);

    @Query("""
        SELECT COUNT(DISTINCT s.nationality)
        FROM Owning o
        JOIN Sticker s ON s.place = o.code
        WHERE o.email = :email
        GROUP BY s.nationality
        HAVING COUNT(s.id) >= 12
    """)
    Long countFinishedCountries(String email);

    @Query("""
        SELECT COUNT(o.id)
        FROM Owning o
        JOIN Sticker s ON s.place = o.code
        WHERE o.email = :email
        AND LOWER(s.type) = 'logo'
    """)
    Long countOwnedLogos(String email);

    @Query("""
        SELECT COUNT(o.id)
        FROM Owning o
        JOIN Sticker s ON s.place = o.code
        WHERE o.email = :email
        AND LOWER(s.type) = 'player'
    """)
    Long countOwnedPlayers(String email);
}