package com.microshop.service.impl;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;
import com.microshop.mapper.CategoryMapper;
import com.microshop.model.Category;
import com.microshop.repository.CategoryRepository;
import com.microshop.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired private CategoryMapper mapper;

    @Autowired private CategoryRepository categoryRepository;

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
}
