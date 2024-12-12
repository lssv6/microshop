package com.microshop.service.impl;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.NewCategoryDTO;
import com.microshop.model.Category;
import com.microshop.repository.CategoryRepository;
import com.microshop.service.CategoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public Optional<CategoryDTO> findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return Optional.of(modelMapper.map(category, CategoryDTO.class));
    }

    @Override
    public CategoryDTO createNested(NewCategoryDTO categoryDTO, Long parentId) {
        Category parentCategory = categoryRepository.findById(parentId).orElseThrow();
        Category childCategory = modelMapper.map(categoryDTO, Category.class);
        childCategory.setParentCategory(parentCategory);
        Category savedCategory = categoryRepository.save(childCategory);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> createNested(NewCategoryDTO... categoryDTOs) {
        Stream<NewCategoryDTO> categoryStream = Stream.of(categoryDTOs);

        // Recursivelly adds the childs to their parents.
        // The sisters: Map and Reduce. I love them!!!
        List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
        Category category = categoryStream
                .map(dto -> modelMapper.map(dto, Category.class))
                .reduce((parent, child) -> {
                    child.setParentCategory(parent);
                    Category cat = categoryRepository.save(parent);
                    categories.add(modelMapper.map(cat, CategoryDTO.class));
                    return child;
                })
                .get();
        Category cat = categoryRepository.save(category);
        categories.add(modelMapper.map(cat, CategoryDTO.class));
        return categories;
    }

    /**
     * Saves the Categories without a category hierarchy.
     */
    @Override
    public List<CategoryDTO> create(NewCategoryDTO... categoryDTOs) {
        List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
        for (NewCategoryDTO categoryDTO : categoryDTOs) {
            Category entity = modelMapper.map(categoryDTO, Category.class);
            CategoryDTO savedEntity = modelMapper.map(categoryRepository.save(entity), CategoryDTO.class);
            categories.add(savedEntity);
        }
        return categories;
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
