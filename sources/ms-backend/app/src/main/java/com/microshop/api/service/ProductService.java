package com.microshop.api.service;

import java.util.Collection;

import com.microshop.api.model.Category;
import com.microshop.api.model.Product;

public interface ProductService{
    public Iterable<Product> findAll();
    public Product save(Product p);
    public Product findById(Long id);
    public Product findByNameLike(String likeString);
    public Collection<Product> findByCategory(Category category);
}

