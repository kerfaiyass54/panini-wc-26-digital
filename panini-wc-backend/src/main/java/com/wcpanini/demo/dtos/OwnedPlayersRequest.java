package com.wcpanini.demo.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnedPlayersRequest {

    private String email;

    private List<String> playerNames;}