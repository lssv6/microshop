package com.microshop.catalog.dto;

import lombok.Data;

@Data
public class ProductDTO{
    private Long id;
    private String code;
    private String name;
    private String description;
    private String technicalInfo;
    private Long categoryId;
}
