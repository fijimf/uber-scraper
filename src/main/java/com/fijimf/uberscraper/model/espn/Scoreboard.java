package com.fijimf.uberscraper.model.espn;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public long numberOfGames() {
        if (sports == null || sports.isEmpty()) {
            return 0L;
        } else {
            return sports.get(0).numberOfGames();
        }
    }
}
