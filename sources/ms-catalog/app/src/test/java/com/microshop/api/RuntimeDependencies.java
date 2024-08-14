package com.microshop.api;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;

public interface RuntimeDependencies{
    @ServiceConnection
    MariaDBContainer<?> MARIADB_CONTAINER = new MariaDBContainer<>("mariadb:11-ubi")
        .withDatabaseName("test_microshop")
        .withUsername("test")
        .withPassword("test");
}
