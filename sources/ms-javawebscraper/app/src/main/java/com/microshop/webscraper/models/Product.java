package com.microshop.webscraper.models;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Product {
    private Integer code;
    private Integer productSpecie;

    private String name;
    private String friendlyName;
    private String description;
    private String tagDescription;
    private Integer weight;

    private String sellerName;
    private Integer sellerId;
    private Integer offerIdMarketplace;

    private String category;
    private String externalUrl;
    private Manufacturer manufacturer;
    private String iframeUrl;
    private String image;
    private List<String> images;

    private Double price;
    private Double primePrice;
    private Double primePriceWithDiscount;

    private Double oldPrice;
    private Double oldPrimePrice;

    private String maxInstallment;

    private Double priceWithDiscount;

    private Double priceMarketplace;

    private Double discountPercentage;

    private Double offerDiscount;

    // I think that is the quantity in the stock system/supply system.
    private Integer quantity;

    private Integer rating;
    private Integer ratingCount;
    private Boolean avaliable;

    // private ???????? preOrderDate;
    private String warranty;
    // private ????????? dateProductArrived;
    // private ????????? html;
    private Map<String, List<String>> photos;

    private String thumbnail;
}
