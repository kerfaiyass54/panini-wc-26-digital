ALTER TABLE player
DROP CONSTRAINT fk_player_team;

SELECT conname
FROM pg_constraint
WHERE conrelid = 'player'::regclass;

ALTER TABLE player
DROP COLUMN team_id;

CREATE TABLE team_players (
                              team_id BIGINT NOT NULL,
                              player_id BIGINT NOT NULL,

                              PRIMARY KEY (team_id, player_id),

                              CONSTRAINT fk_team_players_team
                                  FOREIGN KEY (team_id)
                                      REFERENCES team(id)
                                      ON DELETE CASCADE,

                              CONSTRAINT fk_team_players_player
                                  FOREIGN KEY (player_id)
                                      REFERENCES player(id)
                                      ON DELETE CASCADE
);


CREATE INDEX idx_team_players_team
    ON team_players(team_id);

CREATE INDEX idx_team_players_player
    ON team_players(player_id);


