package com.fijimf.uberscraper.model.espn;

public class ScoreboardTeamOdds {

    private Boolean favorite;
    private Boolean underdog;
    private Integer moneyLine;

    public ScoreboardTeamOdds() {
    }

    public ScoreboardTeamOdds(Boolean favorite, Boolean underdog, Integer moneyLine) {
        this.favorite = favorite;
        this.underdog = underdog;
        this.moneyLine = moneyLine;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getUnderdog() {
        return underdog;
    }

    public void setUnderdog(Boolean underdog) {
        this.underdog = underdog;
    }

    public Integer getMoneyLine() {
        return moneyLine;
    }

    public void setMoneyLine(Integer moneyLine) {
        this.moneyLine = moneyLine;
    }
}
