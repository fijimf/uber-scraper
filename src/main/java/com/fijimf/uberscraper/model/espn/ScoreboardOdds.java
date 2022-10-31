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


}