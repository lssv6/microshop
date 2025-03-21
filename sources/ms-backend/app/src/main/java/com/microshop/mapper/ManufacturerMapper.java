package com.microshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.model.Manufacturer;

@Mapper
public interface ManufacturerMapper{
    public ManufacturerMapper INSTANCE = Mappers.getMapper(ManufacturerMapper.class);
    public ManufacturerDTO manufacturerToManufacturerDTO(Manufacturer manufacturer);
    public Manufacturer manufacturerDTOToManufacturer(ManufacturerDTO manufacturer);
}
