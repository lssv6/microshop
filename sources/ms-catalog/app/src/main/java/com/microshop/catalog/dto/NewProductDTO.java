package com.microshop.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewProductDTO {
    private String code;
    private String name;
    private String description;
    private String technicalInfo;
    private Long categoryId;
}
