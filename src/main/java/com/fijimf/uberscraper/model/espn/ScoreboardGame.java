package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardGame {
    private String id;
    private String uid;

    private LocalDateTime date;
    private String shortName;
    private String location;
    private Integer season;
    private String seasonType;

     "period": 3,
             "clock": "0:00",
             "status": "post",
             "summary": "Final/OT",

    public ScoreboardGame() {
    }

    public ScoreboardGame(String id, String uid, LocalDateTime date, String shortName, String location, Integer season, String seasonType) {
        this.id = id;
        this.uid = uid;
        this.date = date;
        this.shortName = shortName;
        this.location = location;
        this.season = season;
        this.seasonType = seasonType;
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
}
