package com.microshop.service.impl;

import com.microshop.dto.CategoryDTO;
import com.microshop.model.Category;
import com.microshop.repository.CategoryRepository;
import com.microshop.service.CategoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO findById(Long id) {
        return modelMapper.map(categoryRepository.findById(id).get(), CategoryDTO.class);
    }

    @Override
    public void createNested(CategoryDTO... categoryDTOs) {
        Stream<CategoryDTO> categoryStream = Stream.of(categoryDTOs);

        // Recursivelly adds the childs to their parents.
        Category category = categoryStream
                .map(dto -> modelMapper.map(dto, Category.class))
                .reduce((parent, child) -> {
                    child.setParentCategory(parent);
                    categoryRepository.save(parent);
                    return child;
                })
                .get();
        categoryRepository.save(category);
    }

    /**
     * Saves the Categories without a category hierarchy.
     */
    @Override
    public void create(CategoryDTO... categoryDTOs) {
        for (CategoryDTO categoryDTO : categoryDTOs) {
            Category entity = modelMapper.map(categoryDTO, Category.class);
            categoryRepository.save(entity);
        }
    }

    /**
     * Builds a list of the categories following the category hierarchy sequence.
     * @param category - The category where we want to get the breadcrumb.
     * {@return a list of the categories in an }
     * */
    @Override
    public List<CategoryDTO> getBreadcrumb(String path) {
        Category category = categoryRepository.findByPath(path).get();
        return getBreadcrumb(category).stream()
                .map(entity -> modelMapper.map(entity, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    private List<Category> getBreadcrumb(Category category) {
        List<Category> categories = new ArrayList<Category>();
        while (category != null) {
            categories.add(category);
            category = category.getParentCategory();
        }
        return categories.reversed();
    }

    @Override
    public List<CategoryDTO> getBreadcrumb(Long id) {
        Category category = categoryRepository.findById(id).get();
        return getBreadcrumb(category).stream()
                .map(entity -> modelMapper.map(entity, CategoryDTO.class))
                .collect(Collectors.toList());
    }
}
