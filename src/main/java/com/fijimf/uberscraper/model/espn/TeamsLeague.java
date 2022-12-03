package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamsLeague {
    private String id;
    private String slug;
    private TeamsTeam[] teams;

    public TeamsLeague() {
    }

    public TeamsLeague(String id, String slug, TeamsTeam[] teams) {
        this.id = id;
        this.slug = slug;
        this.teams = teams;
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

    public TeamsTeam[] getTeams() {
        return teams;
    }

    public void setTeams(TeamsTeam[] teams) {
        this.teams = teams;
    }
    public List<Team> values(){
       return Arrays.stream(teams).map(TeamsTeam::value).collect(Collectors.toList());
    }
}
