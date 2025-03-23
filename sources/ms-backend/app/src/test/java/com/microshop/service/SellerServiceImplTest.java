package com.microshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.microshop.dto.SellerDTO;
import com.microshop.model.Seller;
import com.microshop.repository.SellerRepository;

@SpringBootTest
public class SellerServiceImplTest{
    private Logger logger = LoggerFactory.getLogger(SellerServiceImplTest.class);

    @MockitoBean private SellerRepository sellerRepository;
    @Autowired private SellerService sellerService;

    @Test
    void testFindById(){
        Seller seller = new Seller();
        seller.setId(5L);
        seller.setName("Kabum");
        seller.setVersion(999L);

        given(sellerRepository.findById(5L)).willReturn(Optional.of(seller));

        SellerDTO sellerDTO = sellerService.findById(5L).get();

        logger.info("SellerDTO returned = {}", sellerDTO);
        assertNotNull(sellerDTO);
        assertEquals(5L, sellerDTO.getId());
        assertEquals("Kabum", sellerDTO.getName());
    }
}
