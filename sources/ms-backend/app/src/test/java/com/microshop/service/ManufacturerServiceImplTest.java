package com.microshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.microshop.model.Manufacturer;
import com.microshop.repository.ManufacturerRepository;

import java.util.Optional;

@SpringBootTest
class ManufacturerServiceImplTest{

    // MockitoBean means that manufacturerRepository is mocked.
    @MockitoBean private ManufacturerRepository manufacturerRepository;
    @Autowired private ManufacturerService manufacturerService;

    private Manufacturer man = new Manufacturer();

    @BeforeEach
    void setUp(){
        man = new Manufacturer();

        man.setId(88L);
        man.setName("Bic Inc.");
        man.setImg("aaa.png");
    }

    @Test void testFindById(){
        // Given
        // This part was been completed by the setUp method.

        // When
        Mockito.when(manufacturerRepository.findById(88L))
            .thenReturn(Optional.of(man));


        // Then
        Manufacturer bic = manufacturerService.findById(88L).get();
        assertNotNull(bic);
        assertEquals(man.getId(), bic.getId());
        assertEquals(man.getName(), bic.getName());
    }


    @Test
    void testFindByName(){
        // Given
        // When
        Mockito.when(manufacturerRepository.findByName("Bic Inc."))
            .thenReturn(Optional.of(man));

        // Then
        Manufacturer bic = manufacturerService.findByName("Bic Inc.").get();
        assertNotNull(bic);
        assertEquals(man.getId(), bic.getId());
        assertEquals(man.getName(), bic.getName());
    }
}
