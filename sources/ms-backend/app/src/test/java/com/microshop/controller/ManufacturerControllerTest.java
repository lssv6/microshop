package com.microshop.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microshop.dto.ManufacturerDTO;
import com.microshop.dto.request.NewManufacturer;
import com.microshop.service.ManufacturerService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebMvcTest(controllers = ManufacturerController.class)
class ManufacturerControllerTest {

    @Autowired // Bean used to convert objects into json and etc.
    private ObjectMapper objectMapper;

    @Autowired private MockMvc mvc;

    @MockitoBean private ManufacturerService manufacturerService;

    @Test
    void testSaveManufacturer() throws Exception {
        NewManufacturer manufacturer = new NewManufacturer();
        manufacturer.setName("Bic Inc.");
        manufacturer.setImg("https://sdfkhj.com/img.png");

        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setName("Bic Inc.");
        manufacturerDTO.setImg("https://sdfkhj.com/img.png");

        given(manufacturerService.save(manufacturer)).willReturn(manufacturerDTO);

        String postBody = objectMapper.writeValueAsString(manufacturer);

        mvc.perform(
                        post("/manufacturers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .content(postBody))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Bic Inc."));
    }

    @Test
    void shouldFindById() throws Exception {
        ManufacturerDTO responseObj = new ManufacturerDTO();
        responseObj.setId(11L);
        responseObj.setName("Apple Inc.");
        responseObj.setImg("https://apple.com/logo.png");

        Mockito.when(manufacturerService.findById(11L)).thenReturn(Optional.of(responseObj));

        mvc.perform(get("/manufacturers/{id}", 11L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(11));
    }

    @Test
    void shouldReturn400WhenNotFound() throws Exception {
        Mockito.when(manufacturerService.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/manufacturers/{id}", 99L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
