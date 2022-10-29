package com.fijimf.uberscraper.integration.util;


import com.fijimf.uberscraper.service.PageLoader;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import io.r2dbc.spi.ConnectionFactory;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.util.*;

public class DockerPostgresDb {
    public static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    public static final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";

    private String containerId;
    private final DockerClient docker;
    private final String containerName;
    private final String port;
    private final String password = "p@ssw0rd";
    private final String image;
    private final Properties savedProperties = new Properties();
    public static final String[] DATASOURCE_KEYS = new String[]{
            SPRING_DATASOURCE_URL,
            SPRING_DATASOURCE_USERNAME,
            SPRING_DATASOURCE_PASSWORD,
            SPRING_DATASOURCE_DRIVER_CLASS_NAME
    };

    public static final Logger logger = LoggerFactory.getLogger(DockerPostgresDb.class);

    public DockerPostgresDb(String image, int port) {
        try {
            this.docker = DefaultDockerClient.fromEnv().build();
            this.containerId = null;
            this.containerName = UUID.randomUUID().toString();
            this.port = Integer.toString(port);
            this.image = image;
        } catch (DockerCertificateException e) {
            throw new RuntimeException(e);
        }
    }

    public void spinUpDatabase() throws DockerException, InterruptedException {
        for (String k : DATASOURCE_KEYS) {
            String v = System.getProperty(k);
            if (v != null) {
                savedProperties.put(k, v);
            }
        }
        System.setProperty(SPRING_DATASOURCE_URL, getUrl());
        System.setProperty(SPRING_DATASOURCE_USERNAME, "postgres");
        System.setProperty(SPRING_DATASOURCE_PASSWORD, password);
        System.setProperty(SPRING_DATASOURCE_DRIVER_CLASS_NAME, "org.postgresql.Driver");


        logger.info("Spinning up database with properties "+getUrl());
        List<PortBinding> hostPorts = new ArrayList<>();
        hostPorts.add(PortBinding.of("0.0.0.0", port));
        Map<String, List<PortBinding>> portBindings = new HashMap<>();
        portBindings.put("5432/tcp", hostPorts);

        HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

        ContainerConfig containerConfig = ContainerConfig
                .builder()
                .hostConfig(hostConfig)
                .image(image)
                .exposedPorts(port + "/tcp")
                .env("POSTGRES_USER=postgres", "POSTGRES_PASSWORD=" + password)
                .build();
        ContainerCreation creation = docker.createContainer(containerConfig, containerName);
        containerId = creation.id();
        docker.startContainer(containerId);
        readyCheck(20, 500L);
    }

    private void readyCheck(int tries, long backoffMillis) {
        logger.info("Waiting for DB to be ready....");
        if (tries <= 0) throw new RuntimeException("Timed out testing db connection");
        try {
            Class.forName(POSTGRESQL_DRIVER);
            DriverManager.getConnection(getUrl(), "postgres", password);
        } catch (Exception e) {
            try {
                Thread.sleep(backoffMillis);
            } catch (InterruptedException ignored) {
            }
            readyCheck(tries - 1, backoffMillis);
        }
    }

    private String getUrl() {
        return "jdbc:postgresql://localhost:" + port + "/postgres";
    }

    public void spinDownDb() throws DockerException, InterruptedException {
        logger.info("Spinning down database.");
        if (containerId != null) {
            logger.info("Stopping container "+containerId);
            docker.stopContainer(containerId, 2);
            logger.info("Removing container "+containerId);
            docker.removeContainer(containerId);
        }
        for (String k : DATASOURCE_KEYS) {
            String v = savedProperties.getProperty(k);
            if (v != null) {
                System.setProperty(k, v);
            }
        }
    }

}


