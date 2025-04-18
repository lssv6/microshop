package com.microshop.controller;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.dto.request.NewManufacturer;
import com.microshop.service.ManufacturerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired private ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<ManufacturerDTO> save(
            @Valid @RequestBody NewManufacturer manufacturerDTO) {
        ManufacturerDTO savedManufacturer = manufacturerService.save(manufacturerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManufacturer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> findById(@PathVariable Long id) {
        Optional<ManufacturerDTO> manufacturerDTO = manufacturerService.findById(id);
        return ResponseEntity.of(manufacturerDTO);
    }

    @GetMapping
    public ResponseEntity<ManufacturerDTO> findByName(
            @RequestParam(name = "name", required = true) String name) {
        Optional<ManufacturerDTO> manufacturerDTO = manufacturerService.findByName(name);
        return ResponseEntity.of(manufacturerDTO);
    }
}
