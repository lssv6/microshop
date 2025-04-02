package com.microshop.service.impl;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.dto.request.NewManufacturer;
import com.microshop.mapper.ManufacturerMapper;
import com.microshop.model.Manufacturer;
import com.microshop.repository.ManufacturerRepository;
import com.microshop.service.ManufacturerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired private ManufacturerMapper mapper;
    @Autowired private ManufacturerRepository manufacturerRepository;

    @Override
    public Optional<ManufacturerDTO> findByName(String id) {
        Optional<ManufacturerDTO> dto =
                manufacturerRepository.findByName(id).map(m -> mapper.toDTO(m));
        return dto;
    }

    @Override
    public Optional<ManufacturerDTO> findById(Long id) {
        Optional<ManufacturerDTO> dto =
                manufacturerRepository.findById(id).map(m -> mapper.toDTO(m));
        return dto;
    }

    @Override
    public ManufacturerDTO save(NewManufacturer newManufacturer) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(newManufacturer.getName());
        manufacturer.setImg(newManufacturer.getImg());

        Manufacturer saved = manufacturerRepository.save(manufacturer);
        return mapper.toDTO(saved);
    }
}
