package com.microshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.microshop.dto.SellerDTO;
import com.microshop.service.SellerService;

@WebMvcTest(controllers = SellerController.class)
public class SellerControllerTest{
    @MockitoBean
    private SellerService sellerService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testFindById() throws Exception{
        SellerDTO seller = new SellerDTO();
        seller.setId(8L);
        seller.setName("Jooj");
        seller.setVersion(999L);
        // Given
        Mockito.when(sellerService.findById(8L)).thenReturn(Optional.of(seller));

        // When - Then
        mvc.perform(
                get("/seller/{id}", 8L)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(8L));
    }
    @Test
    void shouldNotFindByWrongId() throws Exception{
        SellerDTO seller = new SellerDTO();
        seller.setId(8L);
        seller.setName("Jooj");
        seller.setVersion(999L);
        // Given
        Mockito.when(sellerService.findById(8L)).thenReturn(Optional.of(seller));

        // When - Then
        mvc.perform(
                get("/seller/{id}", 10L) // Wrong Id
        ).andExpect(status().is4xxClientError());
    }
}
