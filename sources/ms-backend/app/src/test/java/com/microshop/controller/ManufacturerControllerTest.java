package com.microshop.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.microshop.Mappings;
import com.microshop.dto.ManufacturerDTO;
import com.microshop.model.Manufacturer;
import com.microshop.repository.ManufacturerRepository;
import com.microshop.service.ManufacturerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



@WebMvcTest(controllers = ManufacturerController.class)
class ManufacturerControllerTest{

    @Autowired // Bean used to convert objects into json and etc.
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;


    @MockitoBean private ManufacturerService manufacturerService;

    @Test
    public void testSaveManufacturer(){
        ManufacturerDTO manufacturer = new ManufacturerDTO(10L, "Jooj", "Yeah, yeah", 2L);
        given(manufacturerService.save(manufacturer)).willReturn(manufacturer);

        String postBody = objectMapper.writeValueAsString(manufacturer);

        mvc.perform(post(Mappings.MANUFACTURER).content());
    }
}
