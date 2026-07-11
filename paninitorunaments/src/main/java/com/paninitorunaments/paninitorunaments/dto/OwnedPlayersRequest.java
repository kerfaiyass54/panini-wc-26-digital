package com.paninitorunaments.paninitorunaments.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnedPlayersRequest {

    private String email;

    private List<String> playerNames;
}