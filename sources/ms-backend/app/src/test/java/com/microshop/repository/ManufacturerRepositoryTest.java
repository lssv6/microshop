package com.microshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.microshop.model.Manufacturer;

@DataJpaTest
class ManufacturerRepositoryTest{

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    private static Manufacturer manufacturer;

    @BeforeAll
    static void setUp(){
        manufacturer = new Manufacturer();
        manufacturer.setId(99L);
        manufacturer.setName("Bic");
        manufacturer.setImg("https://www.example.com/mLogo.png");
    }

    @Test
    void testFindById(){
         Manufacturer man = manufacturerRepository.findById(99L).get();
         assertEquals(manufacturer.getImg(), man.getImg());
         assertEquals(manufacturer.getName(), man.getName());

    }
}
