package com.fijimf.uberscraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberScraperApplication {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        SpringApplication.run(UberScraperApplication.class, args);
    }

}
