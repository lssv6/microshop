package com.microshop.controller;

import com.microshop.dto.NewProductDTO;
import com.microshop.dto.ProductDTO;
import com.microshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{code}")
    public ResponseEntity<ProductDTO> getProductByCode(@PathVariable Long code){
       return ResponseEntity.of(productService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createNewProduct(@RequestBody NewProductDTO product) {
        return ResponseEntity.ofNullable(productService.create(product));
    }
}

