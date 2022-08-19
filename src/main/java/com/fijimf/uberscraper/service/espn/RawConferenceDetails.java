package com.fijimf.uberscraper.service.espn;

import java.util.Map;

public class RawConferenceDetails {

    private int espnId;
    private String name;
    private String shortName;
    private String espnLogo;

    public RawConferenceDetails(int espnId, String name, String shortName, String espnLogo) {
        this.espnId = espnId;
        this.name = name;
        this.shortName = shortName;
        this.espnLogo = espnLogo;
    }

    public static <R> RawConferenceDetails fromExtendedDetailsMap(Map<String, Object> map) {
        return new RawConferenceDetails(Integer.parseInt((String) map.get("groupId")), (String) map.get("name"), (String) map.get("shortName"), (String) map.get("logo"));
    }

    public int getEspnId() {
        return espnId;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getEspnLogo() {
        return espnLogo;
    }
}
