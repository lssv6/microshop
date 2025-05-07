package com.microshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;
import com.microshop.model.Category;
import com.microshop.repository.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class CategoryServiceImplTest {

    @MockitoBean private CategoryRepository categoryRepository;

    @Autowired private CategoryService categoryService;

    private static Category category;
    private static Category cat1, cat2, cat3;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(888L);
        category.setName("Hardware");
        category.setFullName("Hardware");
        category.setPath("/hardware");
        category.setFullPath("/hardware");
        category.setParent(null);
        category.setVersion(9L);

        cat1 = new Category();
        cat1.setId(100L);
        cat1.setName("cat1");

        cat2 = new Category();
        cat2.setId(200L);
        cat2.setName("cat2");
        cat2.setParent(cat1);

        cat3 = new Category();
        cat3.setId(300L);
        cat3.setName("cat3");
        cat3.setParent(cat2);
    }

    @Test
    void testFindById() {

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

    @Test
    void testBreadcrumb() {

        given(categoryRepository.findById(100L)).willReturn(Optional.of(cat1));
        given(categoryRepository.findById(200L)).willReturn(Optional.of(cat2));
        given(categoryRepository.findById(300L)).willReturn(Optional.of(cat3));

        // When
        // CategoryDTO category = new CategoryDTO();
        // category.setId(300L);
        // category.setName("cat3");

        List<CategoryDTO> breadcrumb = categoryService.getBreadcrumb(300L);

        // Then
        assertNotNull(breadcrumb);
        assertEquals(breadcrumb.size(), 3);
        assertEquals(breadcrumb.get(0).getId(), 100L);
        assertEquals(breadcrumb.get(1).getId(), 200L);
        assertEquals(breadcrumb.get(2).getId(), 300L);
        assertEquals(breadcrumb.getLast().getName(), "cat3");
    }

    @Test
    void testGetChildrenDeeply() {
        given(categoryRepository.findById(100L)).willReturn(Optional.of(cat1));
        given(categoryRepository.findById(200L)).willReturn(Optional.of(cat2));
        given(categoryRepository.findById(300L)).willReturn(Optional.of(cat3));

        Set<CategoryDTO> cats = categoryService.getChildrenDeeply(100L);

        assertNotNull(cats);
        assertEquals(3, cats.size());
    }
}
