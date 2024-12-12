package com.microshop.service;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.NewCategoryDTO;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public List<CategoryDTO> create(NewCategoryDTO... categoryDTOs);

    public CategoryDTO createNested(NewCategoryDTO categoryDTO, Long parentId);

    public List<CategoryDTO> createNested(NewCategoryDTO... categoryDTOs);

    public List<CategoryDTO> getBreadcrumb(String path);

    public List<CategoryDTO> getBreadcrumb(Long id);

    public Optional<CategoryDTO> findById(Long id);
}
