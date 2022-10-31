package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardGame {
    private String id;
    private String uid;

    private LocalDateTime date;
    private String shortName;
    private String location;
    private Integer season;
    private String seasonType;

    private String period;
    private String clock;
    private String status;
    private String summary;
    private ScoreboardOdds odds;
    private List<ScoreboardTeam> competitors;

    public ScoreboardGame() {
    }

    public ScoreboardGame(String id, String uid, LocalDateTime date, String shortName, String location, Integer season, String seasonType, String period, String clock, String status, String summary, ScoreboardOdds odds, List<ScoreboardTeam> competitors) {
        this.id = id;
        this.uid = uid;
        this.date = date;
        this.shortName = shortName;
        this.location = location;
        this.season = season;
        this.seasonType = seasonType;
        this.period = period;
        this.clock = clock;
        this.status = status;
        this.summary = summary;
        this.odds = odds;
        this.competitors = competitors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getSeasonType() {
        return seasonType;
    }

    public void setSeasonType(String seasonType) {
        this.seasonType = seasonType;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ScoreboardOdds getOdds() {
        return odds;
    }

    public void setOdds(ScoreboardOdds odds) {
        this.odds = odds;
    }

    public List<ScoreboardTeam> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(List<ScoreboardTeam> competitors) {
        this.competitors = competitors;
    }
}
