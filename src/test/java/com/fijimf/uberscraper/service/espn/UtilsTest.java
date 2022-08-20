package com.fijimf.uberscraper.service.espn;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Test
    void seasonToDates() {
        List<LocalDate> dates = Utils.seasonToDates(2016);
        assertThat(dates).first().matches(d -> d.isEqual(LocalDate.of(2016, 11, 1)));
        assertThat(dates).last().matches(d -> d.isEqual(LocalDate.of(2017, 4, 29)));
        assertThat(dates).allMatch(
                d -> d.isAfter(LocalDate.of(2016, 10, 31)) &&
                        d.isBefore(LocalDate.of(2017, 5, 1)));
    }

    @Test
    void testYyyymmdd() {
        List<LocalDate> dates = Utils.seasonToDates(2016);
        assertThat(dates).allMatch(d -> d.equals(Utils.yyyymmdd(Utils.yyyymmdd(d))));
    }
}