package com.fijimf.uberscraper.model.espn;

import java.util.List;

public class Scoreboard {
    private  List<ScoreboardSport> sports;

    public Scoreboard() {
    }

    public Scoreboard(List<ScoreboardSport> sports) {
        this.sports = sports;
    }

    public List<ScoreboardSport> getSports() {
        return sports;
    }
}
