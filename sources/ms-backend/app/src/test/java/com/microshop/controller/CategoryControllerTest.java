package com.microshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;
import com.microshop.service.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private CategoryService categoryService;

    @Autowired private MockMvc mvc;

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setId(10L);
        categoryDTO.setName("Notebooks");
        categoryDTO.setPath("/notebooks");
        categoryDTO.setFullName("Computadores/Notebooks");
        categoryDTO.setFullPath("/computadores/notebooks");
        categoryDTO.setParentId(99L);
    }

    @Test
    void testSaveNestedCategory() throws Exception {
        NewCategory newCategory = new NewCategory();
        newCategory.setName("Notebooks");
        newCategory.setPath("/notebooks");
        newCategory.setParentId(99L);

        // Given
        Mockito.when(categoryService.save(newCategory)).thenReturn(categoryDTO);

        String postBody = objectMapper.writeValueAsString(newCategory);

        // When
        mvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON).content(postBody))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.parentId").value(99L));
    }

    @Test
    void testFindById() throws Exception {
        // Given
        Mockito.when(categoryService.findById(5L)).thenReturn(Optional.of(categoryDTO));

        // When
        mvc.perform(get("/category/5"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.parentId").value(99L))
                .andExpect(jsonPath("$.fullPath").value("/computadores/notebooks"));
    }
}
