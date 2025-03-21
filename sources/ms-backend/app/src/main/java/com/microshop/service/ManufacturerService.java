package com.microshop.service;

import java.util.Optional;

import com.microshop.dto.ManufacturerDTO;

public interface ManufacturerService{
    public Optional<ManufacturerDTO> findById(Long id);
    public Optional<ManufacturerDTO> findByName(String name);

    public ManufacturerDTO save(ManufacturerDTO manufacturerDTO);
}
