package com.microshop.service;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface CategoryService {
    public Optional<CategoryDTO> findById(Long id);

    public Optional<CategoryDTO> findByFullName(String fullName);

    public CategoryDTO save(NewCategory newCategory) throws NoSuchElementException;
}
