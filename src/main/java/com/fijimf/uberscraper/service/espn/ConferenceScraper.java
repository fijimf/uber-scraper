package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;

public class ConferenceScraper {


    public static String allConferenceStandingsUrl(int season) {
        return ESPN_BASE + "/mens-college-basketball/standings/_/season/" + season;
    }


    public static Map<String, String> extractTeamConferenceMapping(Document page) {
        Map<String, String> map = new HashMap<>();
        page.select("div.ResponsiveTable").forEach(e -> {
            String z = e.select("div.Table__Title").text();
            e.select("div.team-link span.hide-mobile a").forEach(f -> {
                map.put(f.attr("href"), z);
            });
        });
        return map;
    }

    public static List<RawConferenceDetails> extractConferenceDetails(Document page) throws JsonProcessingException {
        Elements select = page.select("div#espnfitt+script");
        String json = select.dataNodes().get(0).getWholeData().replace("window['__espnfitt__']=", "");
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(json, Map.class);
        List<Map<String, Object>> extendedDetails = deepListExtract(map, Arrays.asList("page", "content", "headerscoreboard", "collegeConfs"));
        return extendedDetails.stream().map(RawConferenceDetails::fromExtendedDetailsMap).filter(c -> !c.getShortName().equals("Division I")).collect(Collectors.toList());
    }


    private static List<Map<String, Object>> deepListExtract(Map<String, Object> map, List<String> keys) {
        if (keys.isEmpty()) throw new IllegalArgumentException();
        if (map.containsKey(keys.get(0))) {
            if (keys.size() == 1) {
                return (List) map.get(keys.get(0));
            } else {
                return deepListExtract((Map) map.get(keys.get(0)), keys.subList(1, keys.size()));
            }
        } else {
            return Collections.emptyList();
        }
    }


}
