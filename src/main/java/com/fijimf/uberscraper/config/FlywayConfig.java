package com.fijimf.uberscraper.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

// https://stackoverflow.com/a/61412233
@Configuration
public class FlywayConfig {

    private final Environment env;

    public FlywayConfig(final Environment env) {
        this.env = env;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(
                Flyway
                        .configure()
                        .baselineOnMigrate(true)
                        .dataSource(
                                env.getRequiredProperty("spring.flyway.url"),
                                env.getRequiredProperty("spring.flyway.user"),
                                env.getRequiredProperty("spring.flyway.password")
                        )
        );
    }
}
