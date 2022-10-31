package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardOdds {
    private ScoreboardOddsProvider provider;
    private String details;
    private Double overUnder;
    private Double spread;
    private ScoreboardTeamOdds awayTeamOdds;
    private ScoreboardTeamOdds homeTeamOdds;

    public ScoreboardOdds() {
    }

    public ScoreboardOdds(ScoreboardOddsProvider provider, String details, Double overUnder, Double spread, ScoreboardTeamOdds awayTeamOdds, ScoreboardTeamOdds homeTeamOdds) {
        this.provider = provider;
        this.details = details;
        this.overUnder = overUnder;
        this.spread = spread;
        this.awayTeamOdds = awayTeamOdds;
        this.homeTeamOdds = homeTeamOdds;
    }

    public ScoreboardOddsProvider getProvider() {
        return provider;
    }

    public void setProvider(ScoreboardOddsProvider provider) {
        this.provider = provider;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getOverUnder() {
        return overUnder;
    }

    public void setOverUnder(Double overUnder) {
        this.overUnder = overUnder;
    }

    public Double getSpread() {
        return spread;
    }

    public void setSpread(Double spread) {
        this.spread = spread;
    }

    public ScoreboardTeamOdds getAwayTeamOdds() {
        return awayTeamOdds;
    }

    public void setAwayTeamOdds(ScoreboardTeamOdds awayTeamOdds) {
        this.awayTeamOdds = awayTeamOdds;
    }

    public ScoreboardTeamOdds getHomeTeamOdds() {
        return homeTeamOdds;
    }

    public void setHomeTeamOdds(ScoreboardTeamOdds homeTeamOdds) {
        this.homeTeamOdds = homeTeamOdds;
    }
}