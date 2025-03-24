package com.microshop.mapper;

import com.microshop.dto.CategoryDTO;
import com.microshop.model.Category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    public CategoryDTO toDTO(Category category);
}
