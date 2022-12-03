package com.fijimf.uberscraper.model.espn;

import java.util.List;

public class TeamLogo {
    /*
    {
                    "href": "https://a.espncdn.com/i/teamlogos/ncaa/500/2010.png",
                    "alt": "",
                    "rel": [
                      "full",
                      "default"
                    ],
                    "width": 500,
                    "height": 500
                  },
     */

    private String href;
    private String alt;
    private List<String> rel;
    private Integer width;
    private Integer height;

    public TeamLogo() {
    }

    public TeamLogo(String href, String alt, List<String> rel, Integer width, Integer height) {
        this.href = href;
        this.alt = alt;
        this.rel = rel;
        this.width = width;
        this.height = height;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public List<String> getRel() {
        return rel;
    }

    public void setRel(List<String> rel) {
        this.rel = rel;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
