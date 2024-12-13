package com.microshop.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.microshop.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvcTester mvc;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    @DisplayName("Integration test for saving a new unnested category")
    void shouldPersistACategory() throws Exception {
        assertThat(this.mvc.post().uri("/categories").accept(MediaType.APPLICATION_JSON))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson();
        // String newCatJson = asJsonString(new NewCategoryDTO("Canetas", "/canetas"));
        // mvc.perform(post("/categories").content(newCatJson.getBytes("UTF-8")).contentType(MediaType.APPLICATION_JSON))
        //        .andExpect(status().isCreated())
        //        .andExpect(content().json);
    }

    @Test
    void shouldPersistNestedCategories() throws Exception {
        // String cat1 = asJsonString(new NewCategoryDTO("cat1", "/cat1"));
        // String cat2 = asJsonString(new NewCategoryDTO("cat2", "/cat2"));
        // String cat3 = asJsonString(new NewCategoryDTO("cat3", "/cat3"));
        //
        // String saved1 = asJsonString(new CategoryDTO(1L, "cat1", "/cat1"));
        // String saved2 = asJsonString(new CategoryDTO(2L, "cat2", "/cat2"));
        // String saved3 = asJsonString(new CategoryDTO(3L, "cat3", "/cat3"));
        //
        // mvc.perform(post("/categories").content(cat1).contentType(MediaType.APPLICATION_JSON))
        //    .andExpect(status().isCreated())
        //    .andExpect(content().json(saved1));
        // mvc.perform(post("/categories/1").content(cat2).contentType(MediaType.APPLICATION_JSON))
        //    .andExpect(status().isCreated())
        //    .andExpect(content().json(saved2));
        // mvc.perform(post("/categories/2").content(cat3).contentType(MediaType.APPLICATION_JSON))
        //    .andExpect(status().isCreated())
        //    .andExpect(content().json(saved3));
    }
}
