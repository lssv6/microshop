package com.microshop.mapper;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.model.Manufacturer;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ManufacturerMapper {
    public ManufacturerDTO toDTO(Manufacturer manufacturer);

    public Manufacturer toEntity(ManufacturerDTO manufacturer);
}
