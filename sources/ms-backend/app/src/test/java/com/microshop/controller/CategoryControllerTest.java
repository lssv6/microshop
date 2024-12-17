package com.microshop.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.microshop.dto.NewCategoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@AutoConfigureMockMvc
@SpringBootTest
class CategoryControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvcTester mvc;

    @Test
    void shouldPersistACategory() throws Exception {
        NewCategoryDTO newCategoryDTO = new NewCategoryDTO("cat1", "/cat1");
        assertThat(mvc.post()
                        .uri("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newCategoryDTO)))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .extractingPath("$.name")
                .asString()
                .isEqualTo("cat1");
    }

    @Test
    void shouldPersistNestedCategories() throws Exception {
        NewCategoryDTO cat1 = new NewCategoryDTO("cat1", "/cat1");
        NewCategoryDTO cat2 = new NewCategoryDTO("cat2", "/cat2");
        NewCategoryDTO cat3 = new NewCategoryDTO("cat3", "/cat3");

        assertThat(mvc.post()
                        .uri("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cat1)))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .extractingPath("$.name")
                .isEqualTo("cat1");

        assertThat(mvc.post()
                        .uri("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cat2)))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .extractingPath("$.name")
                .isEqualTo("cat2");

        assertThat(mvc.post()
                        .uri("/categories/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cat3)))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .extractingPath("$.name")
                .isEqualTo("cat3");

        // assertThat(mvc.get().uri("/categories/3/breadcrumb").contentType(MediaType.APPLICATION_JSON))
        //        .bodyJson()
        //        .extractingPath("$[0].name")
        //        .isEqualTo("cat1");
    }

    @Test
    void shouldNotPostNullValues() {
        NewCategoryDTO dto = new NewCategoryDTO(null, null);

        assertThat(mvc.post()
                        .uri("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .hasStatus4xxClientError();
    }
}
