package com.microshop.catalog.config;

import org.apache.commons.lang3.Validate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;

@Order(Integer.MIN_VALUE)
public class DevEnvironmentConfigurer implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (environment.getActiveProfiles().length == 0) {
            environment.addActiveProfile("dev");
        }
        if (environment.acceptsProfiles(Profiles.of("dev"))) {
            Validate.validState(
                    environment.getActiveProfiles().length == 1,
                    "Development profile could not be mixed with other profiles");
        }
    }
}
