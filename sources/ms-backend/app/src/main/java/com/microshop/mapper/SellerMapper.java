package com.microshop.mapper;

import com.microshop.dto.SellerDTO;
import com.microshop.model.Seller;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface SellerMapper {
    public SellerDTO toDTO(Seller seller);

    public Seller toEntity(SellerDTO seller);
}
