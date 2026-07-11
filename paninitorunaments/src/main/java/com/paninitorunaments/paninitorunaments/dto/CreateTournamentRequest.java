package com.paninitorunaments.paninitorunaments.dto;

import lombok.Data;

@Data
public class CreateTournamentRequest {

    private String tournament;
    private String email;

}