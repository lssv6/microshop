package com.microshop.api;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

public class LocalCatalogApplication{
    public static void main(String... args){
        System.out.println("HOW");
        var x = SpringApplication.from(CatalogApplication::main)
            .with(RuntimeDependenciesConfiguration.class)
            .run(args);
        //new SpringApplicationBuilder(CatalogApplication.class)
        //    .sources(RuntimeDependenciesConfiguration.class)
        //    //.initializers(new StandaloneApplicationContextInitalizer())
        //    .applicationStartup(new BufferingApplicationStartup(2048))
        //    .profiles("dev")
        //    .banner(
        //        (env, _class, out) -> {
        //            out.println(env);
        //            out.println(_class);
        //            out.println("Pega na minhha e balança");
        //        }
        //    ).bannerMode(Banner.Mode.CONSOLE)
        //    .run(args);
    }

    @TestConfiguration
    @ImportTestcontainers(RuntimeDependencies.class)
    public static class RuntimeDependenciesConfiguration{}
}
