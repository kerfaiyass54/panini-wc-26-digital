// StickerCatalogService.java
package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.StickerSimpleResponse;
import com.wcpanini.demo.repositories.StickerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StickerCatalogService {

    private final StickerRepository stickerRepository;

    public StickerCatalogService(
            StickerRepository stickerRepository
    ) {
        this.stickerRepository = stickerRepository;
    }

    public Page<StickerSimpleResponse> getAllStickers(
            Pageable pageable
    ) {

        return stickerRepository.findAllSimple(pageable);
    }

    public long countByNationalityAndEmail(
            String nationality,
            String email
    ) {

        return stickerRepository
                .countOwnedByNationalityAndEmail(
                        nationality,
                        email
                );
    }

    public List<StickerSimpleResponse> getStickersByNationality(
            String nationality
    ) {

        return stickerRepository
                .findByNationality(nationality).stream()
                .map(sticker -> new StickerSimpleResponse(
                        sticker.getName(),
                        sticker.getNationality(),
                        sticker.getPlace()
                )).toList();
    }
}