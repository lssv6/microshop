package com.microshop.catalog.dto;

import lombok.Data;

@Data
public class NewProductDTO{
    private String code;
    private String name;
    private String description;
    private String technicalInfo;
    private Long categoryId;
}
