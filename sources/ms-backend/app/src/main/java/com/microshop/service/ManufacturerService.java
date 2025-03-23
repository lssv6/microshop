package com.microshop.service;

import com.microshop.dto.ManufacturerDTO;

import java.util.Optional;

public interface ManufacturerService {
    public Optional<ManufacturerDTO> findById(Long id);

    public ManufacturerDTO save(ManufacturerDTO manufacturerDTO);
}
