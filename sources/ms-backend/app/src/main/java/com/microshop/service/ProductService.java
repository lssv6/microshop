package com.microshop.service;

import com.microshop.dto.NewProductDTO;
import com.microshop.dto.ProductDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public ProductDTO create(NewProductDTO p);

    public Optional<ProductDTO> findById(Long id);
    public Optional<ProductDTO> findByCode(Long code);

    public Page<ProductDTO> findByCategory(Long categoryId, Pageable pageable);
}
