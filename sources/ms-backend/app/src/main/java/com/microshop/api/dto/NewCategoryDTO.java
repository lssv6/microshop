package com.microshop.api.dto;

import java.util.Optional;

import lombok.Data;

@Data
public class NewCategoryDTO{
    private String name;
    private Long parentId;
}
