package com.paniniteam.panini_team_management.entities;

import com.paniniteam.panini_team_management.enums.Tactic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "teams")
public class Team extends BaseEntity {

    private String name;

    private String email;

    private List<String> playerCodes;

    private String goalkeeperId;

    private Tactic tactic;
}