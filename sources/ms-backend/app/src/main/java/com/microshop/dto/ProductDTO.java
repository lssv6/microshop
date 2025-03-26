package com.microshop.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String tagDescription;
    private String friendlyName;
    private Long price;
    private Long oldPrice;
    private Long sellerId;
    private Long categoryId;
    private Long manufacturerId;
    private Long version;
}
