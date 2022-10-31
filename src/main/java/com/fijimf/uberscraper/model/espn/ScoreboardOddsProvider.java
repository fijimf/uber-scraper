package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardOddsProvider {

         private String id;
    private String name;
    private String priority;

    public ScoreboardOddsProvider() {
    }

    public ScoreboardOddsProvider(String id, String name, String priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
