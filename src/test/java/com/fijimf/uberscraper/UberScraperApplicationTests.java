package com.fijimf.uberscraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class UberScraperApplicationTests {

    @Test
    void contextLoads() {
        WebDriverManager.chromedriver().setup();
    }

}
