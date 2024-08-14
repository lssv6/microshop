package com.microshop.catalog;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.lifecycle.Startables;

public class StandaloneApplicationContextInitalizer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
    /*** Hold a reference to a MariaDB test container.*/
    private static final MariaDBContainer<?> MARIADB_CONTAINER = new MariaDBContainer<>("mariadb:11-ubi");


    private static Map<String, Object> runDependencies(){
        Startables.deepStart(Stream.of(MARIADB_CONTAINER)).join();

        return Map.of(
            "spring.datasource.url",      MARIADB_CONTAINER.getJdbcUrl(),
            "spring.datasource.username", MARIADB_CONTAINER.getUsername(),
            "spring.datasource.password", MARIADB_CONTAINER.getPassword()
        );
    }

    @Override
    public void initialize(ConfigurableApplicationContext context){
        ConfigurableEnvironment env = context.getEnvironment();
        MapPropertySource dependencies = new MapPropertySource("depencencies", runDependencies());
        env.getPropertySources().addFirst(dependencies);
    }

}
