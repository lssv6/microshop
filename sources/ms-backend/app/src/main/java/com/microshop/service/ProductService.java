package com.microshop.service;


import java.util.Optional;

import com.microshop.model.Product;

public interface ProductService{
    public Optional<Product> findById(Long id);
}
