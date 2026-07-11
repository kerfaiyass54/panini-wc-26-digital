package com.paninitorunaments.paninitorunaments.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_statistics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private Integer tournamentsPlayed;

    private Integer tournamentsWon;

    private Integer matchesPlayed;

    private Integer matchesWon;

    private Integer goalsScored;
}