package com.microshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microshop.model.Seller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SellerRepositoryTest {
    @Autowired
    private SellerRepository sellerRepository;

    @Test
    void shouldPersistSeller() {
        Seller seller = new Seller();
        seller.setName("Manoel Gomes Inc.");
        sellerRepository.save(seller);

        assertEquals(seller, sellerRepository.findByName("Manoel Gomes Inc.").get());
    }

    @Test
    void sellerNameMustBeNotBlank() {}
}
