package com.microshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microshop.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldPersistOneRootCategory() {
        Category category = new Category();
        category.setName("Canetas");
        category.setPath("/canetas");
        categoryRepository.save(category);
        assertEquals(category, categoryRepository.findByName("Canetas").get());
    }

    @Test
    void shouldSaveOneChildCategory() {
        Category category = new Category();
        category.setName("Canetas");
        category.setPath("/canetas");

        categoryRepository.save(category);

        Category category2 = new Category();
        category2.setName("Caneta azul");
        category2.setPath("/azuis");
        category2.setParentCategory(category);
        categoryRepository.save(category2);

        assertEquals(category, categoryRepository.findByPath("/azuis").get().getParentCategory());
    }
}
