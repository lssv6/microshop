package com.microshop.catalog;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;


@SpringBootTest(
    classes = CatalogApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
    //properties = { "spring.datasource.url=jdbc:tc:mariadb:11-ubi:///databasename" }
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
        //Startables.deepStart(Stream.of(MARIADB)).join();
        MARIADB.start();
        System.out.println(MARIADB.getJdbcUrl());
        System.out.println(MARIADB.getUsername());
        System.out.println(MARIADB.getPassword());
        registry.add("spring.datasource.url", MARIADB::getJdbcUrl);
        registry.add("spring.datasource.username", MARIADB::getUsername);
        registry.add("spring.datasource.password", MARIADB::getPassword);
    }

}
