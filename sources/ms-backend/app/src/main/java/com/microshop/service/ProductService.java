package com.microshop.service;

import com.microshop.dto.ProductDTO;
import com.microshop.dto.request.NewProduct;

import java.util.Optional;

public interface ProductService {
    public ProductDTO save(NewProduct product);

    public Optional<ProductDTO> findById(Long id);
}
