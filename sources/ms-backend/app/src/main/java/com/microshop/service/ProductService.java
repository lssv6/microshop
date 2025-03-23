package com.microshop.service;

import com.microshop.model.Product;

import java.util.Optional;

public interface ProductService {
    public Optional<Product> findById(Long id);
}
