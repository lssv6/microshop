package com.microshop.config;

import com.github.slugify.Slugify;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Slugify getSlugfier() {
        Slugify slugify = Slugify.builder().build();
        return slugify;
    }
}
