package com.paninitorunaments.paninitorunaments.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer ability;

    private String nationality;

    private String position;

    @ManyToMany(mappedBy = "players")
    private List<Team> teams = new ArrayList<>();
}