package com.microshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewCategoryDTO {
    private String name;
    private String path;
}
