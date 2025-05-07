package com.microshop.service.impl;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;
import com.microshop.mapper.CategoryMapper;
import com.microshop.model.Category;
import com.microshop.repository.CategoryRepository;
import com.microshop.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    public static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired private CategoryMapper mapper;

    @Autowired private CategoryRepository categoryRepository;

    @Override
    public Optional<CategoryDTO> findByFullName(String fullName) {
        return categoryRepository.findByFullName(fullName).map(c -> mapper.toDTO(c));
    }

    @Override
    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id).map(c -> mapper.toDTO(c));
    }

    private static String ensureSlashAsFirstChar(String path) {
        return path.startsWith("/") ? path : "/" + path;
    }

    @Override
    public CategoryDTO save(NewCategory categoryDTO) throws NoSuchElementException {
        String name = categoryDTO.getName();
        String path = categoryDTO.getPath();
        path = ensureSlashAsFirstChar(path);
        Long parentId = categoryDTO.getParentId();

        Category toSave = new Category();
        toSave.setName(name);
        toSave.setPath(path);

        // If a parent is specified.
        if (parentId != null) {
            // Then try to get the parent from db
            Category parent = categoryRepository.findById(parentId).orElseThrow();

            // The child must concatenate the path and names;
            String parentFullName = parent.getFullName();
            String parentFullPath = parent.getFullPath();

            toSave.setFullName("%s/%s".formatted(parentFullName, name));
            toSave.setFullPath("%s%s".formatted(parentFullPath, path));
            toSave.setParent(parent);
        } else {
            toSave.setFullName(toSave.getName());
            toSave.setFullPath(toSave.getPath());
        }
        Category savedCategory = categoryRepository.save(toSave);

        return mapper.toDTO(savedCategory);
    }

    @Override
    public List<CategoryDTO> getBreadcrumb(Long id) {
        List<Category> breadcrumb = new ArrayList<>();
        Category category = categoryRepository.findById(id).orElseThrow();
        while (category != null) {
            breadcrumb.addFirst(category);
            category = category.getParent();
        }
        List<CategoryDTO> breadcrumbDTO =
                breadcrumb.stream().map(c -> mapper.toDTO(c)).collect(Collectors.toList());

        return breadcrumbDTO;
    }

    @Override
    public Set<CategoryDTO> getChildren(Long id) {
        Optional<Category> parent = categoryRepository.findById(id);
        if (parent.isEmpty()) {
            throw new NoSuchElementException("Couldn't find the parent category");
        }
        Set<Category> children = categoryRepository.findByParent(parent.get());
        return children.stream().map(c -> mapper.toDTO(c)).collect(Collectors.toSet());
    }

    @Override
    public Optional<CategoryDTO> findByFullPath(String fullPath) {
        return categoryRepository.findByFullPath(fullPath).map(c -> mapper.toDTO(c));
    }
}
