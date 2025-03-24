package com.microshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;
import com.microshop.model.Category;
import com.microshop.repository.CategoryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@SpringBootTest
class CategoryServiceImplTest {
    @MockitoBean private CategoryRepository categoryRepository;

    @Autowired private CategoryService categoryService;

    @Test
    void testFindById() {
        Category category = new Category();
        category.setId(888L);
        category.setName("Hardware");
        category.setFullName("Hardware");
        category.setPath("/hardware");
        category.setFullPath("/hardware");
        category.setParent(null);
        category.setVersion(9L);

        // Given
        given(categoryRepository.findById(2L)).willReturn(Optional.of(category));

        // When
        CategoryDTO foundCat = categoryService.findById(2L).get();

        // Then
        assertNotNull(foundCat);
        assertEquals("Hardware", foundCat.getName());
    }

    @Test // kueizy
    void testSaveNestedChild() {

        // Given
        Category parent = new Category();
        parent.setId(10L);
        parent.setName("Computadores");
        parent.setPath("/computadores");
        parent.setFullName("/Computadores");
        parent.setFullPath("/computadores");
        parent.setParent(null);
        given(categoryRepository.findById(10L)).willReturn(Optional.of(parent));

        Category savedCategory = new Category();
        savedCategory.setId(99L);
        savedCategory.setName("Notebooks");
        savedCategory.setPath("/notebooks");
        savedCategory.setFullName("Computadores/Notebooks");
        savedCategory.setFullPath("/computadores/notebooks");
        savedCategory.setParent(parent);
        given(categoryRepository.save(any())).willReturn(savedCategory);

        // When
        NewCategory newCategory = new NewCategory();
        newCategory.setName("Notebooks");
        newCategory.setPath("notebooks");
        newCategory.setParentId(10L);

        CategoryDTO saved = categoryService.save(newCategory);

        // Then
        assertNotNull(saved);
        assertEquals(99L, saved.getId());
        assertEquals("Notebooks", saved.getName());
        assertEquals("/notebooks", saved.getPath());
        assertEquals("Computadores/Notebooks", saved.getFullName());
        assertEquals("/computadores/notebooks", saved.getFullPath());
        assertEquals(10L, saved.getParentId());
    }
}
