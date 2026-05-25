// StickerController.java
package com.wcpanini.demo.controllers;

import com.wcpanini.demo.dtos.AddStickerRequest;
import com.wcpanini.demo.dtos.CheckStickerRequest;
import com.wcpanini.demo.dtos.DuplicateRequest;
import com.wcpanini.demo.entities.Duplicate;
import com.wcpanini.demo.entities.Owning;
import com.wcpanini.demo.services.StickerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stickers")
@CrossOrigin("*")
public class StickerController {

    private final StickerService stickerService;

    public StickerController(StickerService stickerService) {
        this.stickerService = stickerService;
    }

    @PostMapping("/owning")
    public ResponseEntity<?> addSticker(
            @RequestBody AddStickerRequest request
    ) {

        try {

            Owning owning = stickerService.addSticker(
                    request.getEmail(),
                    request.getCode()
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(owning);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/has")
    public ResponseEntity<Boolean> hasSticker(
            @RequestBody CheckStickerRequest request
    ) {

        boolean result = stickerService.hasSticker(
                request.getEmail(),
                request.getPlace()
        );

        return ResponseEntity.ok(result);
    }

    @PostMapping("/duplicate")
    public ResponseEntity<?> duplicateSticker(
            @RequestBody DuplicateRequest request
    ) {

        try {

            Duplicate duplicate = stickerService.duplicateSticker(
                    request.getEmail(),
                    request.getPlace()
            );

            return ResponseEntity.ok(duplicate);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/duplicate/increase")
    public ResponseEntity<?> increaseDuplicate(
            @RequestBody DuplicateRequest request
    ) {

        try {

            Duplicate duplicate = stickerService.increaseDuplicate(
                    request.getEmail(),
                    request.getPlace()
            );

            return ResponseEntity.ok(duplicate);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/ownings/{email}")
    public ResponseEntity<Page<Owning>> getOwnings(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Owning> ownings = stickerService.getOwnings(
                email,
                pageable
        );

        return ResponseEntity.ok(ownings);
    }

    @GetMapping("/duplicates/{email}")
    public ResponseEntity<Page<Duplicate>> getDuplicates(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Duplicate> duplicates = stickerService.getDuplicates(
                email,
                pageable
        );

        return ResponseEntity.ok(duplicates);
    }

    @DeleteMapping("/duplicate")
    public ResponseEntity<?> removeDuplicate(
            @RequestParam String email,
            @RequestParam String place
    ) {

        try {

            stickerService.removeDuplicate(email, place);

            return ResponseEntity
                    .ok("Duplicate removed successfully");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}