package com.microshop.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microshop.dto.NewProductDTO;
import com.microshop.model.Product;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mm.createTypeMap(NewProductDTO.class, Product.class).addMappings(
            mapper -> {
                mapper.skip(Product::setId);
            }
        );
        return mm;

    }
}
