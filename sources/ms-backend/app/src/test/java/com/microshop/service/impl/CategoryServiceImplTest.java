package com.microshop.service.impl;

import com.microshop.dto.NewCategoryDTO;
import com.microshop.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldSaveAndReadCategory() {
        var category = new NewCategoryDTO("Canetas", "/canetas");
    }

    @Test
    void successFullyRetrieveAnBreadcrumb() {
        NewCategoryDTO category1 = new NewCategoryDTO("cat1", "/cat1");
        NewCategoryDTO category2 = new NewCategoryDTO("cat2", "/cat2");
        NewCategoryDTO category3 = new NewCategoryDTO("cat1", "/cat3");
        categoryService.createNested(category1, category2, category3);
        // assertEquals(List.of(category1, category2, category3), categoryService.getBreadcrumb());
    }
}
