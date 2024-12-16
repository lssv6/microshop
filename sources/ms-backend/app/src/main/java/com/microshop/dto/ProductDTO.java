package com.microshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    @NotNull
    private Long code;

    @NotBlank
    private String name;

    @NotBlank
    private String friendlyName;

    private String description;
    private String tagDescription;

    @NotNull
    private SellerDTO seller;

    @NotNull
    private CategoryDTO category;

    @NotNull
    @PositiveOrZero
    private Double price;

    @PositiveOrZero
    private Double oldPrice;

    private String warranty;
}
