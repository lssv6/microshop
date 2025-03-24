package com.microshop.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String fullName;

    private String path;
    private String fullPath;

    private Long parentId;
    private Long version;
}
