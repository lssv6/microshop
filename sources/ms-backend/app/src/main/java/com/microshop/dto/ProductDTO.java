package com.microshop.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long code;
    private String name;
    private String friendlyName;
    private String description;
    private String tagDescription;
    private SellerDTO seller;
    private CategoryDTO category;
    private Double price;
    private Double oldPrice;
    private String warranty;
}
