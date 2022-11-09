package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static final String ESPN_BASE = "https://www.espn.com";


    public static List<LocalDate> seasonToDates(int year) {
        List<LocalDate> l = new ArrayList<>();
        for (LocalDate d = LocalDate.of(year, 11, 1);
             d.isBefore(LocalDate.of(year + 1, 4, 30));
             d = d.plusDays(1))
            l.add(d);
        return l;
    }





    public static String yyyymmdd(LocalDate d) {
        return d.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static LocalDate yyyymmdd(String s) {
        return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
