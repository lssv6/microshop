package com.microshop.service;

import com.microshop.dto.NewCategoryDTO;
import com.microshop.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        categoryService = new CategoryServiceImpl();
    }

    @Test
    void cannotCreateASellerWithNullName() {
        var toSave = new NewCategoryDTO();
        categoryService.create(toSave);
    }

    @Test
    void createAndRetrieveBreadcrumbs() {
        NewCategoryDTO category1 = new NewCategoryDTO("cat1", "/cat1");
        NewCategoryDTO category2 = new NewCategoryDTO("cat2", "/cat2");
        NewCategoryDTO category3 = new NewCategoryDTO("cat1", "/cat3");
        categoryService.createNested(category1, category2, category3);
        categoryService.getBreadcrumb("/cat3");
    }
}
