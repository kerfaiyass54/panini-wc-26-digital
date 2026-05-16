package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {

    long countByTypeIgnoreCase(String type);

}
