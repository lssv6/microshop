package com.microshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.mapper.ManufacturerMapper;
import com.microshop.model.Manufacturer;
import com.microshop.repository.ManufacturerRepository;
import com.microshop.service.ManufacturerService;


@Service
public class ManufacturerServiceImpl implements ManufacturerService{
    @Autowired
    private ManufacturerMapper mapper;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public Optional<ManufacturerDTO> findById(Long id){
        Optional<ManufacturerDTO> dto= manufacturerRepository.findById(id)
            .map(m -> mapper.manufacturerToManufacturerDTO(m));
        return dto;
    }

    public Optional<ManufacturerDTO> findByName(String name){
        Optional<ManufacturerDTO> dto = manufacturerRepository.findByName(name)
            .map(m -> mapper.manufacturerToManufacturerDTO(m));
        return dto;
    }

    public ManufacturerDTO save(ManufacturerDTO manufacturerDTO){
        Manufacturer entity = mapper.manufacturerDTOToManufacturer(manufacturerDTO);
        Manufacturer saved = manufacturerRepository.save(entity);
        return mapper.manufacturerToManufacturerDTO(saved);
    }
}
