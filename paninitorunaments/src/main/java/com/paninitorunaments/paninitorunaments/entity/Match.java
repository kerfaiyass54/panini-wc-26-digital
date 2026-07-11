package com.paninitorunaments.paninitorunaments.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "football_match")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean played = false;

    @Column(name = "journey")
    private Integer journey;

    @ManyToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    private Integer goalsHome;

    private Integer goalsAway;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id")
    private List<Goal> goals = new ArrayList<>();
}