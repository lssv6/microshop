package com.microshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class NewProduct {

    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotBlank(message = "Product description must not be blank")
    private String description;

    @NotBlank(message = "Product tagDescription must not be blank")
    private String tagDescription;

    @PositiveOrZero private Long price;

    @PositiveOrZero private Long oldPrice;

    private Long sellerId;
    private Long categoryId;
    private Long manufacturerId;
}
