package com.microshop.service;

import com.microshop.repository.CategoryRepository;
import com.microshop.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService; // Looks that must be implemented

    @BeforeEach
    void setup() {
        categoryService = new CategoryServiceImpl();
    }

    @Test
    void cannotCreateASellerWithNullName() {
        // Category toSave = new Category();
        // toSave.setName("Category");
        // toSave.setPath("/cat");
        // when(categoryRepository.save(refEq(toSave))).thenReturn(toSave);

        // assertThat(categoryService.create());
    }

    @Test
    void createAndRetrieveBreadcrumbs() {
        // Category
    }
}
