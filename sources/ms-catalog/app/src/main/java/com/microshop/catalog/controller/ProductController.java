package com.microshop.api.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microshop.api.dto.ProductDTO;
import com.microshop.api.model.Category;
import com.microshop.api.model.Product;
import com.microshop.api.service.CategoryService;
import com.microshop.api.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController{
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;
    
    ////////////////////////////////////////////////////////////////////////////
    // CREATE
    ////////////////////////////////////////////////////////////////////////////
    @PostMapping
    public ProductDTO addNewProduct(@RequestBody ProductDTO p){
        Product prod = mapper.map(p, Product.class);
        Long catId = p.getCategoryId();
        if(catId != null){
            Optional<Category> category = categoryService.findById(catId);
            category.ifPresent(c -> prod.setCategory(c));
        }
        productService.save(prod);

        return mapper.map(prod, ProductDTO.class);
    }
    ////////////////////////////////////////////////////////////////////////////
    // READ
    ////////////////////////////////////////////////////////////////////////////

    @GetMapping
    public Iterable<ProductDTO> getAllProducts(){
        var products = productService.findAll();
        var convertedProducts = new HashSet<ProductDTO>();
        products.forEach(p -> convertedProducts.add(mapper.map(p, ProductDTO.class)));
        return convertedProducts;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return productService.findById(id);
    }
}
