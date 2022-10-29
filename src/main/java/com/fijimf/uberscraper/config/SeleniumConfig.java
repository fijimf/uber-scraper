package com.fijimf.uberscraper.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class SeleniumConfig {

    @Bean
    public ChromeDriver seleniumDriver(){
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
    //    options.setHeadless(true);
        options.setScriptTimeout(Duration.ofMillis(500));
        options.setImplicitWaitTimeout(Duration.ofSeconds(2));
        options.setPageLoadTimeout(Duration.ofSeconds(10));
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        ChromeDriver chromeDriver = new ChromeDriver(options);
        return chromeDriver;
    }

}
