package com.microshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.microshop.dto.SellerDTO;
import com.microshop.model.Seller;

@Mapper(componentModel = ComponentModel.SPRING)
public interface SellerMapper{
    public SellerDTO stoDTO(Seller seller);
    public Seller stoEntity(SellerDTO seller);
}
