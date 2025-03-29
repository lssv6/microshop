package com.microshop.controller;

import com.microshop.dto.ProductDTO;
import com.microshop.dto.request.NewProduct;
import com.microshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> save(NewProduct newProduct) {
        ProductDTO productDTO = productService.save(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.of(productService.findById(id));
    }
}
