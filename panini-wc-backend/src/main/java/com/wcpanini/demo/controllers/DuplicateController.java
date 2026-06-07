package com.wcpanini.demo.controllers;

import com.wcpanini.demo.dtos.DuplicateRequest;
import com.wcpanini.demo.dtos.DuplicateResponse;
import com.wcpanini.demo.entities.Duplicate;
import com.wcpanini.demo.services.DuplicateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/duplicates")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DuplicateController {

    private final DuplicateService duplicateService;

    @GetMapping("/{email}")
    public ResponseEntity<List<DuplicateResponse>>
    getDuplicates(
            @PathVariable
            String email
    ) {

        return ResponseEntity.ok(
                duplicateService.getDuplicates(
                        email
                )
        );
    }

    @PatchMapping("/reduce")
    public ResponseEntity<Void>
    reduceDuplicate(
            @RequestBody
            DuplicateRequest request
    ) {

        duplicateService.reduceDuplicate(
                request
        );

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void>
    deleteDuplicate(
            @RequestBody
            DuplicateRequest request
    ) {

        duplicateService.deleteDuplicate(
                request
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}