package com.microshop.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NewCategory {
    @NotBlank(message = "Category name must not be blank")
    private String name;

    @NotBlank(message = "Category path must not be blank")
    private String path;

    private Long parentId;
}
