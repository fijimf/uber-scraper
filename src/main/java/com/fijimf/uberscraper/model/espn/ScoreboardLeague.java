package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardLeague {
    private  String id;
    private  String uid;
    private  String name;
    private  String abbreviation;
    private  String shortName;
    private  String slug;
    private  boolean isTournament;
    private  List<ScoreboardGame> events;

    public ScoreboardLeague() {
    }

    public ScoreboardLeague(String id, String uid, String name, String abbreviation, String shortName, String slug, boolean isTournament, List<ScoreboardGame> events) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.abbreviation = abbreviation;
        this.shortName = shortName;
        this.slug = slug;
        this.isTournament = isTournament;
        this.events = events;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSlug() {
        return slug;
    }

    public boolean isTournament() {
        return isTournament;
    }

    public List<ScoreboardGame> getEvents() {
        return events;
    }
}
