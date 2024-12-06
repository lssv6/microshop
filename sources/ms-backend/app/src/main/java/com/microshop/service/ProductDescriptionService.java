package com.microshop.service;

import com.microshop.model.ProductDescription;

public interface ProductDescriptionService {
    public ProductDescription create(ProductDescription p);

    public ProductDescription modify(ProductDescription old, ProductDescription newPD);
}
