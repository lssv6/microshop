package com.microshop.service;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public interface CategoryService {
    public Optional<CategoryDTO> findById(Long id);

    public Optional<CategoryDTO> findByFullName(String fullName);

    public Optional<CategoryDTO> findByFullPath(String fullPath);

    public CategoryDTO save(NewCategory newCategory) throws NoSuchElementException;

    public List<CategoryDTO> getBreadcrumb(Long id);

    public Set<CategoryDTO> getChildren(Long id);

    public Set<CategoryDTO> getChildrenDeeply(Long id);
}
