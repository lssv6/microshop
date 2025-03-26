package com.microshop.dto.request;

import lombok.Data;

@Data
public class NewProduct {
    private String name;
    private String description;
    private String tagDescription;
    private Long price;
    private Long oldPrice;
    private Long sellerId;
    private Long categoryId;
    private Long manufacturerId;
}
