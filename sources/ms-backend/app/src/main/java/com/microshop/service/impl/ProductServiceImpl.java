package com.microshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.microshop.model.Product;
import com.microshop.repository.ProductRepository;
import com.microshop.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }
}
