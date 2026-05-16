package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Sticker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {

    long countByTypeIgnoreCase(String type);


    Page<Sticker> findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrNationalityContainingIgnoreCaseOrPlaceContainingIgnoreCase(
            String name,
            String type,
            String nationality,
            String place,
            Pageable pageable
    );
}
