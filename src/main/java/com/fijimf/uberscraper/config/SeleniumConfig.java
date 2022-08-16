package com.fijimf.uberscraper.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public ChromeDriver seleniumDriver(){
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.setHeadless(true);
        return new ChromeDriver(options);
    }

}
