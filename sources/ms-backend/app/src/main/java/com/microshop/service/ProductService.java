package com.microshop.service;

import com.microshop.dto.ProductDTO;
import com.microshop.dto.request.NewProduct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public ProductDTO save(NewProduct product);

    public Optional<ProductDTO> findById(Long id);

    public Page<ProductDTO> findByCategoryIdNested(List<Long> categoryIds, Pageable pageable);

    public Page<ProductDTO> findByCategoryId(Long id, Pageable pageable);
}
