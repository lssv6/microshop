package com.microshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microshop.Mappings;
import com.microshop.dto.ManufacturerDTO;
import com.microshop.service.ManufacturerService;

@RestController
@RequestMapping(Mappings.MANUFACTURER)
public class ManufacturerController{

    @Autowired
    private ManufacturerService manufacturerService;

    @PostMapping
    public  ManufacturerDTO save(@RequestBody ManufacturerDTO manufacturerDTO){
        return manufacturerService.save(manufacturerDTO);
    }
}
