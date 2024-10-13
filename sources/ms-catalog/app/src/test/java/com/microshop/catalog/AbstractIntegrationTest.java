package com.microshop.catalog;


import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.lifecycle.Startables;


@SpringBootTest(
    classes = CatalogApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    static MariaDBContainer<?> MARIADB = new MariaDBContainer<>("mariadb:11-ubi");

    @BeforeAll
    static void beforeAll(){

        var isRunning = MARIADB.isRunning();
        System.out.printf("MARIADB is running? = %b\n",isRunning);
        if(!isRunning)
            MARIADB.start();
        
    }

    @AfterAll
    static void afterAll(){
        MARIADB.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        System.out.println("Starting containers.");
        Startables.deepStart(Stream.of(MARIADB)).join();
        System.out.println("Started containers.");

        System.out.println("Configuring connection properties.");
        System.out.printf("Database connection string = %s\n", MARIADB.getJdbcUrl());
        System.out.printf("Databse username = %s\n", MARIADB.getUsername());
        System.out.printf("Database password = %s\n", MARIADB.getPassword());

        registry.add("spring.datasource.url", MARIADB::getJdbcUrl);
        registry.add("spring.datasource.username", MARIADB::getUsername);
        registry.add("spring.datasource.password", MARIADB::getPassword);
    }

}
