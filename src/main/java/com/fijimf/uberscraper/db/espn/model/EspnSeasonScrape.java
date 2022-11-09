package com.fijimf.uberscraper.db.espn.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "espn_season_scrape")
public class EspnSeasonScrape {
    @Id
    private long id;
    private int season;
    private LocalDate from;
    private LocalDate to;

    @Column("started_at")
    private LocalDateTime startedAt;
    @Column("completed_at")
    private LocalDateTime completedAt;

    private String status;

    public EspnSeasonScrape() {
    }

    public EspnSeasonScrape(long id, int season, LocalDate from, LocalDate to, LocalDateTime startedAt, LocalDateTime completedAt, String status) {
        this.id = id;
        this.season = season;
        this.from = from;
        this.to = to;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private EspnSeasonScrape complete(String status) {
        setCompletedAt(LocalDateTime.now());
        setStatus(status);
        return this;
    }

    public EspnSeasonScrape success() {
        return complete("SUCCESS");
    }

    public EspnSeasonScrape cancel() {
        return complete("CANCELLED");
    }

    public EspnSeasonScrape error() {
        return complete("ERROR");
    }

    public EspnSeasonScrape timeout() {
        return complete("TIMEOUT");
    }
}
