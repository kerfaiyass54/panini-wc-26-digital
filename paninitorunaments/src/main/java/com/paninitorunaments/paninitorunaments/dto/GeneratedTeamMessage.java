package com.paninitorunaments.paninitorunaments.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeneratedTeamMessage {

    private String teamId;

    private String email;

    private String teamName;

    private List<GeneratedPlayer> players;
}