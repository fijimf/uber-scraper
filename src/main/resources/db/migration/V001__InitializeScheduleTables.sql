CREATE TABLE espn_season_scrape
(
    id           BIGSERIAL PRIMARY KEY,
    season       INT       NOT NULL,
    started_at   TIMESTAMP NOT NULL,
    aborted_at   TIMESTAMP NULL,
    completed_at TIMESTAMP NULL
);

CREATE TABLE espn_scoreboard_scrape
(
    id                    BIGSERIAL PRIMARY KEY,
    espn_season_scrape_id BIGINT      NOT NULL REFERENCES espn_season_scrape (id),
    scoreboard_key        DATE        NOT NULL,
    url                   VARCHAR(96) NOT NULL,
    retrieved_at          TIMESTAMP   NULL,
    response_time_ms      BIGINT      NULL,
    response_code         INT         NULL,
    response              TEXT        NULL,
    number_of_game_ids    INT         NULL
);

CREATE TABLE espn_game_scrape
(
    id                        BIGSERIAL PRIMARY KEY,
    espn_scoreboard_scrape_id BIGINT      NOT NULL REFERENCES espn_scoreboard_scrape (id),
    game_key                  VARCHAR(16) NOT NULL,
    retrieved_at              TIMESTAMP   NOT NULL,
    response_time_ms          INT         NOT NULL,
    response_code             INT         NOT NULL,
    response_size             INT         NOT NULL,
    game_details              VARCHAR(32) NULL,
    home_rank                 VARCHAR(3)  NULL,
    home_name                 VARCHAR(32) NULL,
    home_short_name           VARCHAR(32) NULL,
    home_long_name            VARCHAR(32) NULL,
    home_abbrev               VARCHAR(5)  NULL,
    home_score                VARCHAR(3)  NULL,
    away_rank                 VARCHAR(3)  NULL,
    away_name                 VARCHAR(32) NULL,
    away_short_name           VARCHAR(32) NULL,
    away_long_name            VARCHAR(32) NULL,
    away_abbrev               VARCHAR(5)  NULL,
    away_score                VARCHAR(3)  NULL,
    num_periods               INT         NULL,
    status_detail             VARCHAR(12) NULL,
    game_date                 DATE        NULL,
    game_time                 TIME        NULL,
    game_loc                  VARCHAR(32) NULL,
    favorite                  VARCHAR(5)  NULL,
    spread                    FLOAT       NULL,
    over_under                FLOAT       NULL
);
