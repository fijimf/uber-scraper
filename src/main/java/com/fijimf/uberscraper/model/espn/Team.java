package com.fijimf.uberscraper.model.espn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
/*
        "id": "2006",
                "uid": "s:40~l:41~t:2006",
                "slug": "akron-zips",
                "abbreviation": "AKR",
                "displayName": "Akron Zips",
                "shortDisplayName": "Akron",
                "name": "Zips",
                "nickname": "Akron",
                "location": "Akron",
                "color": "00285e",
                "alternateColor": "84754e",
                "isActive": true,
                "isAllStar": false,
                "logos": [
                  {
                    "href": "https://a.espncdn.com/i/teamlogos/ncaa/500/2006.png",
                    "alt": "",
                    "rel": [
                      "full",
                      "default"
                    ],
                    "width": 500,
                    "height": 500
                  },
                  {
                    "href": "https://a.espncdn.com/i/teamlogos/ncaa/500-dark/2006.png",
                    "alt": "",
                    "rel": [
                      "full",
                      "dark"
                    ],
                    "width": 500,
                    "height": 500
                  }
                ],
                "links": [
                  {
                    "language": "en-US",
                    "rel": [
                      "clubhouse",
                      "desktop",
                      "team"
                    ],
                    "href": "https://www.espn.com/mens-college-basketball/team/_/id/2006/akron-zips",
                    "text": "Clubhouse",
                    "shortText": "Clubhouse",
                    "isExternal": false,
                    "isPremium": false
                  },
                  {
                    "language": "en-US",
                    "rel": [
                      "roster",
                      "desktop",
                      "team"
                    ],
                    "href": "http://www.espn.com/mens-college-basketball/team/roster/_/id/2006",
                    "text": "Roster",
                    "shortText": "Roster",
                    "isExternal": false,
                    "isPremium": false
                  },
                  {
                    "language": "en-US",
                    "rel": [
                      "stats",
                      "desktop",
                      "team"
                    ],
                    "href": "http://www.espn.com/mens-college-basketball/team/stats/_/id/2006",
                    "text": "Statistics",
                    "shortText": "Statistics",
                    "isExternal": false,
                    "isPremium": false
                  },
                  {
                    "language": "en-US",
                    "rel": [
                      "schedule",
                      "desktop",
                      "team"
                    ],
                    "href": "https://www.espn.com/mens-college-basketball/team/schedule/_/id/2006",
                    "text": "Schedule",
                    "shortText": "Schedule",
                    "isExternal": false,
                    "isPremium": false
                  },
                  {
                    "language": "en",
                    "rel": [
                      "tickets",
                      "desktop",
                      "team"
                    ],
                    "href": "https://www.vividseats.com/ncaab/akron-zips-tickets.html?wsUser=717",
                    "text": "Tickets",
                    "isExternal": true,
                    "isPremium": false
                  }
                ]
              }
            },
 */

    private String id;
    private String  slug;
    private String  abbreviation;
    private String  displayName;
    private String shortDisplayName;
    private String name;
    private String   nickname;
    private String  color;
    private String  alternateColor;

    private TeamLogo[] logos;

    public Team() {
    }

    public Team(String id, String slug, String abbreviation, String displayName, String shortDisplayName, String name, String nickname, String color, String alternateColor, TeamLogo[] logos) {
        this.id = id;
        this.slug = slug;
        this.abbreviation = abbreviation;
        this.displayName = displayName;
        this.shortDisplayName = shortDisplayName;
        this.name = name;
        this.nickname = nickname;
        this.color = color;
        this.alternateColor = alternateColor;
        this.logos = logos;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getShortDisplayName() {
        return shortDisplayName;
    }

    public void setShortDisplayName(String shortDisplayName) {
        this.shortDisplayName = shortDisplayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public TeamLogo[] getLogos() {
        return logos;
    }

    public void setLogos(TeamLogo[] logos) {
        this.logos = logos;
    }
}
