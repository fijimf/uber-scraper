package com.fijimf.uberscraper.db.espn.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "espn_scoreboard_scrape")
public class EspnScoreboardScrape {

    @Id
    private long id;
    @Column( "espn_season_scrape_id")
    private long espnSeasonScrapeId;
    @Column( "scoreboard_key")
    private LocalDate scoreboardKey;
    private String url;

    @Column( "retrieved_at")
    private LocalDateTime retrievedAt;

    @Column("response_time_ms")
    private Long responseTimeMs;
    @Column("response_code")
    private Integer responseCode;

    private String response;
    @Column("number_of_game_ids")
    private Integer numberOfGameIds;

    public EspnScoreboardScrape() {
    }

    public EspnScoreboardScrape(long id, long espnSeasonScrapeId, LocalDate scoreboardKey, String url, LocalDateTime retrievedAt, Long responseTimeMs, Integer responseCode, String response, Integer numberOfGameIds) {
        this.id = id;
        this.espnSeasonScrapeId = espnSeasonScrapeId;
        this.scoreboardKey = scoreboardKey;
        this.url = url;
        this.retrievedAt = retrievedAt;
        this.responseTimeMs = responseTimeMs;
        this.responseCode = responseCode;
        this.response = response;
        this.numberOfGameIds = numberOfGameIds;
    }

    public EspnScoreboardScrape(long espnSeasonScrapeId, LocalDate scoreboardKey, String url) {
        this.id = 0L;
        this.espnSeasonScrapeId = espnSeasonScrapeId;
        this.scoreboardKey = scoreboardKey;
        this.url = url;
        this.retrievedAt = null;
        this.responseTimeMs = null;
        this.responseCode = null;
        this.response = null;
        this.numberOfGameIds = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEspnSeasonScrapeId() {
        return espnSeasonScrapeId;
    }

    public void setEspnSeasonScrapeId(long espnSeasonScrapeId) {
        this.espnSeasonScrapeId = espnSeasonScrapeId;
    }

    public LocalDate getScoreboardKey() {
        return scoreboardKey;
    }

    public void setScoreboardKey(LocalDate scoreboardKey) {
        this.scoreboardKey = scoreboardKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getRetrievedAt() {
        return retrievedAt;
    }

    public void setRetrievedAt(LocalDateTime retrievedAt) {
        this.retrievedAt = retrievedAt;
    }

    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getNumberOfGameIds() {
        return numberOfGameIds;
    }

    public void setNumberOfGameIds(Integer numberOfGameIds) {
        this.numberOfGameIds = numberOfGameIds;
    }
}
