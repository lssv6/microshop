package com.microshop.catalog.dto;

import lombok.Data;

@Data
public class NewCategoryDTO {
    private String name;
    private Long parentId;
}
