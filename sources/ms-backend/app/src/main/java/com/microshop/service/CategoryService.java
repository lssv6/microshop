package com.microshop.service;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.NewCategoryDTO;
import java.util.List;

public interface CategoryService {
    public void create(NewCategoryDTO... categoryDTOs);

    public CategoryDTO findById(Long id);

    public void createNested(NewCategoryDTO... categoryDTOs);

    public List<CategoryDTO> getBreadcrumb(String path);

    public List<CategoryDTO> getBreadcrumb(Long id);
}
