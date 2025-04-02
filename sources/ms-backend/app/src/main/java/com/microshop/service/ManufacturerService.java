package com.microshop.service;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.dto.request.NewManufacturer;

import java.util.Optional;

public interface ManufacturerService {
    public Optional<ManufacturerDTO> findById(Long id);

    public Optional<ManufacturerDTO> findByName(String name);

    public ManufacturerDTO save(NewManufacturer manufacturerDTO);
}
