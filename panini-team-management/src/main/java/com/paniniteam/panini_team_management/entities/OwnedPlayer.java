package com.paniniteam.panini_team_management.entities;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ownings")
public class OwnedPlayer {

    private String email;

    private List<String> codes;
}
