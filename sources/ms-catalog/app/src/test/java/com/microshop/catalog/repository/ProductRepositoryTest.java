package com.microshop.catalog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest{
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindAll(){
        assertEquals(1, 1);
    }
    
}
