package com.microshop.catalog.service;

import java.util.Collection;

import com.microshop.catalog.model.Category;
import com.microshop.catalog.model.Product;

public interface ProductService{
    public Iterable<Product> findAll();
    public Product save(Product p);
    public Product findById(Long id);
    public Product findByNameLike(String likeString);
    public Collection<Product> findByCategory(Category category);
}

