package com.microshop.dto;

import lombok.Data;

@Data
public class NewProductDTO {
    private String name;
    private String friendlyName;
    private String description;
    private String tagDescription;
    private Long sellerId;
    private Long categoryId;
    private Double price;
    private Double oldPrice;
    private String warranty;
}
