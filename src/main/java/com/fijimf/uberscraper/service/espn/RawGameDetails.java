package com.fijimf.uberscraper.service.espn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RawGameDetails {
    private String gameDetails;
    private String homeRank;
    private String homeName;
    private String homeShortName;
    private String homeLongName;
    private String homeAbbrev;
    private String homeScore;
    private String awayRank;
    private String awayName;
    private String awayShortName;
    private String awayLongName;
    private String awayAbbrev;
    private String awayScore;
    private Integer numPeriods;
    private String statusDetail;
    private LocalDate date;
    private LocalDateTime dateTime;
    private String location;
    private String favorite;
    private Double spread;
    private Double overUnder;

    public RawGameDetails(String gameDetails, String homeRank, String homeName, String homeShortName, String homeLongName, String homeAbbrev, String homeScore, String awayRank, String awayName, String awayShortName, String awayLongName, String awayAbbrev, String awayScore, Integer numPeriods, String statusDetail, LocalDate date, LocalDateTime dateTime, String location, String favorite, Double spread, Double overUnder) {
        this.gameDetails = gameDetails;
        this.homeRank = homeRank;
        this.homeName = homeName;
        this.homeShortName = homeShortName;
        this.homeLongName = homeLongName;
        this.homeAbbrev = homeAbbrev;
        this.homeScore = homeScore;
        this.awayRank = awayRank;
        this.awayName = awayName;
        this.awayShortName = awayShortName;
        this.awayLongName = awayLongName;
        this.awayAbbrev = awayAbbrev;
        this.awayScore = awayScore;
        this.numPeriods = numPeriods;
        this.statusDetail = statusDetail;
        this.date = date;
        this.dateTime = dateTime;
        this.location = location;
        this.favorite = favorite;
        this.spread = spread;
        this.overUnder = overUnder;
    }

    public String getGameDetails() {
        return gameDetails;
    }

    public String getHomeRank() {
        return homeRank;
    }

    public String getHomeName() {
        return homeName;
    }

    public String getHomeShortName() {
        return homeShortName;
    }

    public String getHomeLongName() {
        return homeLongName;
    }

    public String getHomeAbbrev() {
        return homeAbbrev;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public String getAwayRank() {
        return awayRank;
    }

    public String getAwayName() {
        return awayName;
    }

    public String getAwayShortName() {
        return awayShortName;
    }

    public String getAwayLongName() {
        return awayLongName;
    }

    public String getAwayAbbrev() {
        return awayAbbrev;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public Integer getNumPeriods() {
        return numPeriods;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getFavorite() {
        return favorite;
    }

    public Double getSpread() {
        return spread;
    }

    public Double getOverUnder() {
        return overUnder;
    }
}
