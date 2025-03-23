package com.microshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.model.Manufacturer;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ManufacturerMapper{
    public ManufacturerDTO toDTO(Manufacturer manufacturer);
    public Manufacturer toEntity(ManufacturerDTO manufacturer);
}
