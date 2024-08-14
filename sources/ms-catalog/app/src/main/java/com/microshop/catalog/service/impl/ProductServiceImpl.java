package com.microshop.catalog.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microshop.catalog.model.Category;
import com.microshop.catalog.model.Product;
import com.microshop.catalog.repository.ProductRepository;
import com.microshop.catalog.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Iterable<Product> findAll(){
        return productRepository.findAll();
    };
    
    @Override 
    public Product save(Product p){
        return productRepository.save(p);
    }

    @Override
    public Product findById(Long id){
        return productRepository.findById(id).get();
    }
    @Override
    public Product findByNameLike(String likeString){
        return productRepository.findByNameLike(likeString);
    }

    @Override
    public Collection<Product> findByCategory(Category category){
        return productRepository.findByCategory(category);
    }
}


