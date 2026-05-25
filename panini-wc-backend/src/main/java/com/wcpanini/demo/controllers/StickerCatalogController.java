// StickerCatalogController.java
package com.wcpanini.demo.controllers;

import com.wcpanini.demo.dtos.StickerSimpleResponse;
import com.wcpanini.demo.services.StickerCatalogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalog")
@CrossOrigin("*")
public class StickerCatalogController {

    private final StickerCatalogService stickerCatalogService;

    public StickerCatalogController(
            StickerCatalogService stickerCatalogService
    ) {
        this.stickerCatalogService = stickerCatalogService;
    }

    @GetMapping("/stickers")
    public ResponseEntity<Page<StickerSimpleResponse>> getAllStickers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<StickerSimpleResponse> stickers =
                stickerCatalogService.getAllStickers(pageable);

        return ResponseEntity.ok(stickers);
    }
}