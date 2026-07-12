-- =========================================
-- PLAYER
-- =========================================
CREATE TABLE player (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        ability INTEGER NOT NULL,
                        nationality VARCHAR(100),
                        position VARCHAR(50)
);

-- =========================================
-- TEAM
-- =========================================
CREATE TABLE team (
                      id BIGSERIAL PRIMARY KEY,

                      name VARCHAR(255) NOT NULL,

                      email VARCHAR(255) NOT NULL,

                      date_create TIMESTAMP NOT NULL
);

-- =========================================
-- TEAM PLAYERS
-- =========================================
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

-- =========================================
-- MATCH
-- =========================================
CREATE TABLE football_match (

                                id BIGSERIAL PRIMARY KEY,

                                team1_id BIGINT NOT NULL,
                                team2_id BIGINT NOT NULL,

                                goals_home INTEGER DEFAULT 0,
                                goals_away INTEGER DEFAULT 0,

                                played BOOLEAN DEFAULT FALSE,

                                journey INTEGER,

                                CONSTRAINT fk_match_team1
                                    FOREIGN KEY (team1_id)
                                        REFERENCES team(id),

                                CONSTRAINT fk_match_team2
                                    FOREIGN KEY (team2_id)
                                        REFERENCES team(id),

                                CONSTRAINT chk_different_teams
                                    CHECK (team1_id <> team2_id)
);

-- =========================================
-- GOAL
-- =========================================
CREATE TABLE goal (

                      id BIGSERIAL PRIMARY KEY,

                      minute INTEGER NOT NULL,

                      player_id BIGINT NOT NULL,

                      match_id BIGINT NOT NULL,

                      CONSTRAINT fk_goal_player
                          FOREIGN KEY (player_id)
                              REFERENCES player(id)
                              ON DELETE CASCADE,

                      CONSTRAINT fk_goal_match
                          FOREIGN KEY (match_id)
                              REFERENCES football_match(id)
                              ON DELETE CASCADE
);

-- =========================================
-- CHAMPIONNAT
-- =========================================
CREATE TABLE championnat (

                             id BIGSERIAL PRIMARY KEY,

                             tournament VARCHAR(255) NOT NULL,

                             email VARCHAR(255) NOT NULL,

                             winner_processed BOOLEAN DEFAULT FALSE
);

-- =========================================
-- CHAMPIONNAT TEAMS
-- =========================================
CREATE TABLE championnat_teams (

                                   championnat_id BIGINT NOT NULL,
                                   team_id BIGINT NOT NULL,

                                   PRIMARY KEY (championnat_id, team_id),

                                   CONSTRAINT fk_ct_championnat
                                       FOREIGN KEY (championnat_id)
                                           REFERENCES championnat(id)
                                           ON DELETE CASCADE,

                                   CONSTRAINT fk_ct_team
                                       FOREIGN KEY (team_id)
                                           REFERENCES team(id)
                                           ON DELETE CASCADE
);

-- =========================================
-- CHAMPIONNAT MATCHES
-- =========================================
CREATE TABLE championnat_matches (

                                     championnat_id BIGINT NOT NULL,
                                     match_id BIGINT NOT NULL,

                                     PRIMARY KEY (championnat_id, match_id),

                                     CONSTRAINT fk_cm_championnat
                                         FOREIGN KEY (championnat_id)
                                             REFERENCES championnat(id)
                                             ON DELETE CASCADE,

                                     CONSTRAINT fk_cm_match
                                         FOREIGN KEY (match_id)
                                             REFERENCES football_match(id)
                                             ON DELETE CASCADE
);

-- =========================================
-- STANDINGS
-- =========================================
CREATE TABLE standing (

                          id BIGSERIAL PRIMARY KEY,

                          championnat_id BIGINT NOT NULL,
                          team_id BIGINT NOT NULL,

                          played INTEGER DEFAULT 0,
                          won INTEGER DEFAULT 0,
                          drawn INTEGER DEFAULT 0,
                          lost INTEGER DEFAULT 0,

                          goals_for INTEGER DEFAULT 0,
                          goals_against INTEGER DEFAULT 0,
                          goal_difference INTEGER DEFAULT 0,

                          points INTEGER DEFAULT 0,

                          CONSTRAINT fk_standing_championnat
                              FOREIGN KEY (championnat_id)
                                  REFERENCES championnat(id)
                                  ON DELETE CASCADE,

                          CONSTRAINT fk_standing_team
                              FOREIGN KEY (team_id)
                                  REFERENCES team(id)
                                  ON DELETE CASCADE
);

-- =========================================
-- USER STATISTICS
-- =========================================
CREATE TABLE user_statistics (

                                 id BIGSERIAL PRIMARY KEY,

                                 email VARCHAR(255) NOT NULL UNIQUE,

                                 tournaments_played INTEGER DEFAULT 0,

                                 tournaments_won INTEGER DEFAULT 0,

                                 matches_played INTEGER DEFAULT 0,

                                 matches_won INTEGER DEFAULT 0,

                                 goals_scored INTEGER DEFAULT 0
);

-- =========================================
-- INDEXES
-- =========================================
CREATE INDEX idx_team_email
    ON team(email);

CREATE INDEX idx_championnat_email
    ON championnat(email);

CREATE INDEX idx_user_statistics_email
    ON user_statistics(email);

CREATE INDEX idx_match_journey
    ON football_match(journey);

CREATE INDEX idx_goal_player
    ON goal(player_id);

CREATE INDEX idx_goal_match
    ON goal(match_id);