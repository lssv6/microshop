package com.microshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDTO {
    @NotNull
    private Long code;

    @NotBlank
    private String name;

    @NotBlank
    private String friendlyName;

    private String description;
    private String tagDescription;

    //@NotNull
    private SellerDTO seller;

    //@NotNull
    private CategoryDTO category;

    @NotNull
    @PositiveOrZero
    private Long price;

    @PositiveOrZero
    private Long oldPrice;

    private String warranty;
}
