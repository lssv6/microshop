package com.microshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microshop.dto.NewSellerDTO;
import com.microshop.dto.SellerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {

    private SellerService sellerService;

    @Test
    void shouldSaveSeller() {
        SellerDTO seller = sellerService.create(new NewSellerDTO("Joao"));
        SellerDTO savedSeller = sellerService.findById(seller.getId());

        assertEquals(seller, savedSeller);
    }
}
