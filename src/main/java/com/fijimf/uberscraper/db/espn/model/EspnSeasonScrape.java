package com.fijimf.uberscraper.db.espn.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "espn_season_scrape")
public class EspnSeasonScrape {
    @Id
    private long id;
    private int season;
    @Column( "started_at")
    private LocalDateTime startedAt;

    @Column( "aborted_at")
    private LocalDateTime abortedAt;

    @Column( "completed_at")
    private LocalDateTime completedAt;

    public EspnSeasonScrape() {
    }

    public EspnSeasonScrape(long id, int season, LocalDateTime startedAt, LocalDateTime abortedAt, LocalDateTime completedAt) {
        this.id = id;
        this.season = season;
        this.startedAt = startedAt;
        this.abortedAt = abortedAt;
        this.completedAt = completedAt;
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

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getAbortedAt() {
        return abortedAt;
    }

    public void setAbortedAt(LocalDateTime abortedAt) {
        this.abortedAt = abortedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
