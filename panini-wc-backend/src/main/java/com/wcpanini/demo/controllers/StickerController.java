package com.wcpanini.demo.controllers;

import com.wcpanini.demo.dtos.StickerDTO;
import com.wcpanini.demo.dtos.StickerStatsDTO;
import com.wcpanini.demo.services.StickerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stickers")
public class StickerController {

    private final StickerService stickerService;

    public StickerController(StickerService stickerService) {
        this.stickerService = stickerService;
    }

    // GET /api/stickers?page=0&size=20&sortBy=id
    @GetMapping
    public ResponseEntity<Page<StickerDTO>> findAll(
            @RequestParam(defaultValue = "0")  int    page,
            @RequestParam(defaultValue = "20") int    size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(stickerService.findAll(page, size, sortBy));
    }

    // POST /api/stickers
    @PostMapping
    public ResponseEntity<StickerDTO> create(@RequestBody StickerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stickerService.create(dto));
    }

    // PUT /api/stickers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<StickerDTO> update(@PathVariable Long id, @RequestBody StickerDTO dto) {
        return ResponseEntity.ok(stickerService.update(id, dto));
    }

    // DELETE /api/stickers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stickerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StickerDTO>> search(

            @RequestParam String query,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size
    ) {

        return ResponseEntity.ok(
                stickerService.search(
                        query,
                        page,
                        size
                )
        );
    }

    @GetMapping("/stats")
    public ResponseEntity<StickerStatsDTO> getStats() {
        return ResponseEntity.ok(stickerService.getStats());
    }

    @GetMapping("/logos")
    public ResponseEntity<List<String>>  getLogos() {
        return ResponseEntity.ok(stickerService.getLogoNationalities());
    }
}