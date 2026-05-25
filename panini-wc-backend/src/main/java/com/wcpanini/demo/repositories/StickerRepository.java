package com.wcpanini.demo.repositories;

import com.wcpanini.demo.dtos.StickerSimpleResponse;
import com.wcpanini.demo.entities.Sticker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {

    long countByTypeIgnoreCase(String type);

    @Query("""
        SELECT COUNT(s)
        FROM Sticker s
        WHERE LOWER(s.type) = LOWER(:type)
    """)
    long countType(String type);

    @Query("""
        SELECT new com.wcpanini.demo.dtos.StickerSimpleResponse(
            s.name,
            s.nationality,
            s.place
        )
        FROM Sticker s
        ORDER BY s.place ASC
    """)
    Page<StickerSimpleResponse> findAllSimple(Pageable pageable);
}
