package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardTeam {

        private String id;
        private String uid;

        private String homeAway;
        private Boolean winner;
        private String score;

        private String logo;
        private String logoDark;
        private String displayName;
        private String name;
        private String abbreviation;
        private String location;
        private String color;
        private String alternateColor;
        private String group;

    public ScoreboardTeam() {
    }

    public ScoreboardTeam(String id, String uid, String homeAway, Boolean winner, String score, String logo, String logoDark, String displayName, String name, String abbreviation, String location, String color, String alternateColor, String group) {
        this.id = id;
        this.uid = uid;
        this.homeAway = homeAway;
        this.winner = winner;
        this.score = score;
        this.logo = logo;
        this.logoDark = logoDark;
        this.displayName = displayName;
        this.name = name;
        this.abbreviation = abbreviation;
        this.location = location;
        this.color = color;
        this.alternateColor = alternateColor;
        this.group = group;
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

    public String getHomeAway() {
        return homeAway;
    }

    public void setHomeAway(String homeAway) {
        this.homeAway = homeAway;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoDark() {
        return logoDark;
    }

    public void setLogoDark(String logoDark) {
        this.logoDark = logoDark;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAlternateColor() {
        return alternateColor;
    }

    public void setAlternateColor(String alternateColor) {
        this.alternateColor = alternateColor;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
