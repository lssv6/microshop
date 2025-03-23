package com.microshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import com.microshop.dto.ManufacturerDTO;
import com.microshop.model.Manufacturer;
import com.microshop.repository.ManufacturerRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@SpringBootTest
class ManufacturerServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(ManufacturerServiceImplTest.class);
    // MockitoBean means that manufacturerRepository is mocked.

    @MockitoBean private ManufacturerRepository manufacturerRepository;
    @Autowired private ManufacturerService manufacturerService;

    @Test
    void testFindById() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(88L);
        manufacturer.setName("Apple Inc.");
        manufacturer.setImg("aaa.png");

        given(manufacturerRepository.findById(88L)).willReturn(Optional.of(manufacturer));

        ManufacturerDTO manufacturerDTO = manufacturerService.findById(88L).get();

        logger.info("ManufacturerDTO returned = {}", manufacturerDTO);
        assertNotNull(manufacturerDTO);
        assertEquals(88L, manufacturerDTO.getId());
        assertEquals("Apple Inc.", manufacturerDTO.getName());
    }
}
