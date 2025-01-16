package com.microshop.controller;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

// @WebMvcTest(ProductController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Test
    void shouldPersistAProduct(@Autowired MockMvc mvc) throws Exception {
        //        String newCatJson = "{\"name\":\"Canetas\", \"path\":\"/canetas\"}";
        //        String expectedResultJson = "{\"id\":1,\"name\":\"Canetas\", \"path\":\"/canetas\"}";
        //        mvc.perform(post("/products").content(newCatJson.getBytes("UTF-8")))
        //                .andExpect(status().isOk())
        //                .andExpect(content().json(expectedResultJson));
        // NewProductDTO product = new NewProductDTO("Celulares", "/celulares");
        // mockMvc.perform(post(URI.create("/products")).content("{\"name\":\"Celulares\", \"path\":\"/celulares\"}"))
        //        .andExpect(status().isOk());

    }
}
