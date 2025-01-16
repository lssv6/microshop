package com.microshop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDTO {
    // @NotBlank(message = "Name cannot be blank")
    @NotBlank
    private String name;

    // @NotBlank(message = "Path cannot be blank")
    @NotBlank
    private String path;
}
