package com.microshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldPersistAProduct() throws Exception {
        // NewProductDTO product = new NewProductDTO("Celulares", "/celulares");

        mockMvc.perform(post(URI.create("/products")).content("{\"name\":\"Celulares\", \"path\":\"/celulares\"}"))
                .andExpect(status().isOk());
    }
}
