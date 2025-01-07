package com.microshop.service.impl;

import com.microshop.dto.NewProductDTO;
import com.microshop.dto.ProductDTO;
import com.microshop.model.Category;
import com.microshop.model.Product;
import com.microshop.repository.CategoryRepository;
import com.microshop.repository.ProductRepository;
import com.microshop.service.ProductService;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired 
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO create(NewProductDTO p) {
        Product product = modelMapper.map(p, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id).map(x -> modelMapper.map(x, ProductDTO.class));
    }

    @Override
    public Page<ProductDTO> findByCategory(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        Page<Product> products = productRepository.findByCategory(category , pageable);
        return products.map(p -> modelMapper.map(p, ProductDTO.class));
    }
}
