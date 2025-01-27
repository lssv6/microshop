package com.microshop.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        // mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // mm.createTypeMap(NewProductDTO.class, Product.class).addMappings(
        //    mapper -> {
        //        mapper.skip(Product::setId);
        //    }
        // );
        return mm;
    }
}
