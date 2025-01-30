package com.microshop.dto.dataimport;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
class Manufacturer {
    private Long id;
    private String name;
    private String img;
}

@Data
public class ProductPageData {
    private Long code;
    private Long productSpecie;

    private String name;
    private String friendlyName;
    private String description;
    private String tagDescription;
    private Long weight;

    private String sellerName;
    private Long sellerId;
    private Long offerIdMarketplace;

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
    private Long quantity;

    private Long rating;
    private Long ratingCount;
    private Boolean avaliable;

    // private ???????? preOrderDate;
    private String warranty;
    // private ????????? dateProductArrived;
    // private ????????? html;
    private Map<String, List<String>> photos;

    private String thumbnail;
}
