package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamsSport {
    private String id;
    private String slug;

    private TeamsLeague[] leagues;

    public TeamsSport() {
    }

    public TeamsSport(String id, String slug, TeamsLeague[] leagues) {
        this.id = id;
        this.slug = slug;
        this.leagues = leagues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public TeamsLeague[] getLeagues() {
        return leagues;
    }

    public void setLeagues(TeamsLeague[] leagues) {
        this.leagues = leagues;
    }

    public List<Team> values() {
       return Arrays.stream(leagues).findFirst().map(TeamsLeague::values).orElse(Collections.emptyList());
    }
}
