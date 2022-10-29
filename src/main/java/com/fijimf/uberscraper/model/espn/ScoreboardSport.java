package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardSport {
    private String id;
    private String uid;
    private String name;
    private String slug;
    private List<ScoreboardLeague> leagues;

    public ScoreboardSport() {
    }

    public ScoreboardSport(String id, String uid, String name, String slug, List<ScoreboardLeague> leagues) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.slug = slug;
        this.leagues = leagues;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public List<ScoreboardLeague> getLeagues() {
        return leagues;
    }
}
