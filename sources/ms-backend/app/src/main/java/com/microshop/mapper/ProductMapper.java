package com.microshop.mapper;

import com.microshop.dto.ProductDTO;
import com.microshop.model.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "manufacturerId", source = "manufacturer.id")
    public ProductDTO toDTO(Product product);
}
