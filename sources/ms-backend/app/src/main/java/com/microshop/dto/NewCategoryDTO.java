package com.microshop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String path;
}
