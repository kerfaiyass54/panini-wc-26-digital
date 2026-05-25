// StickerCatalogService.java
package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.StickerSimpleResponse;
import com.wcpanini.demo.repositories.StickerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<StickerSimpleResponse> getStickersByNationality(
            String nationality,
            Pageable pageable
    ) {

        return stickerRepository
                .findByNationality(nationality, pageable)
                .map(sticker -> new StickerSimpleResponse(
                        sticker.getName(),
                        sticker.getNationality(),
                        sticker.getPlace()
                ));
    }
}