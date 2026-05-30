package com.wcpanini.demo.controllers;

import com.wcpanini.demo.entities.Invitation;
import com.wcpanini.demo.services.RelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RelationController {

    private final RelationService relationService;

    @PostMapping("/invite")
    public ResponseEntity<?> sendInvite(
            @RequestParam String sender,
            @RequestParam String receiver
    ) {
        try {
            relationService.sendInvite(sender, receiver);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Invitation sent successfully");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/invite/{id}/status")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        try {

            relationService.changeStatus(id, status);

            return ResponseEntity
                    .ok("Invitation status updated");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<String>> getRelations(
            @PathVariable String email
    ) {

        List<String> relations =
                relationService.getRelations(email);

        return ResponseEntity.ok(relations);
    }

    @GetMapping("/{email}/available")
    public ResponseEntity<List<String>> getNotConnectedUsers(
            @PathVariable String email
    ) {

        List<String> users =
                relationService.getNotConnectedUsers(email);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/invitations/pending/{receiver}")
    public ResponseEntity<List<Invitation>> getPendingInvitations(
            @PathVariable String receiver
    ) {

        List<Invitation> invitations =
                relationService.getPendingInvitations(receiver);

        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/invitations/sent/pending/{sender}")
    public ResponseEntity<List<Invitation>> getSentPendingInvitations(
            @PathVariable String sender
    ) {

        List<Invitation> invitations =
                relationService.getSentPendingInvitations(sender);

        return ResponseEntity.ok(invitations);
    }

}