package com.fijimf.uberscraper.db.espn.repo;

import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import com.fijimf.uberscraper.integration.util.DockerPostgresDb;
import com.spotify.docker.client.exceptions.DockerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@EntityScan(basePackages = {"com.fijimf.uberscraper.db.espn"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EspnSeasonScrapeRepoTest {
    private static final DockerPostgresDb dockerDb = new DockerPostgresDb("postgres:13-alpine", 59432);

    @Autowired
    EspnSeasonScrapeRepo essRepo;

    @BeforeAll
    public static void spinUpDatabase() throws DockerException, InterruptedException {
        dockerDb.spinUpDatabase();
    }

    @AfterAll
    public static void spinDownDb() throws DockerException, InterruptedException {
        dockerDb.spinDownDb();
    }

    @Test
    public void contextLoads() {
        assertThat(essRepo).isNotNull();
    }

    @Test
    public void saveOne() {
        EspnSeasonScrape s = essRepo.save(new EspnSeasonScrape(0L, 2022, LocalDateTime.now(), null, LocalDateTime.now().plusSeconds(6))).block();
        assertThat(s).isNotNull();
        assertThat(s.getId()).isGreaterThan(0L);
    }

    @Test
    public void saveStream() {
        Flux<EspnSeasonScrape> flux = Flux.range(2000, 20).map(y -> new EspnSeasonScrape(0L, y, LocalDateTime.now(), null, LocalDateTime.now().plusSeconds(3)));
        EspnSeasonScrape s = essRepo.saveAll(flux).blockLast();
        assertThat(s).isNotNull();
        assertThat(s.getId()).isGreaterThan(0L);
    }


//    @Test
//    public void saveModelRun() {
//        Model model = modelRepo.save(new Model("key", "Name"));
//        Season season = seasonRepo.save(new Season(1999));
//        ModelRun modelRun = modelRunRepo.save(new ModelRun(model, season.getId(), LocalDateTime.now()));
//        List<ModelRun> modelRuns = modelRunRepo.findAll();
//        assertThat(modelRuns).hasSize(1);
//    }
//
//    @Test
//    public void saveSeries() {
//        Model model = modelRepo.save(new Model("key", "Name"));
//        Season season = seasonRepo.save(new Season(1999));
//        ModelRun modelRun = modelRunRepo.save(new ModelRun(model, season.getId(), LocalDateTime.now()));
//
//        Statistic stat = statRepo.save(new Statistic("xxx", "XXX", model.getId(), true, null, "%0.7f"));
//        Series ser1 = seriesRepo.save(new Series(modelRun, stat, List.of()));
//        Series ser = ser1;
//        assertThat(ser.getId()).isGreaterThan(0L);
//
//    }
//

}
