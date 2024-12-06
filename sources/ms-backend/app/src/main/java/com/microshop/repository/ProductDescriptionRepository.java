package com.microshop.repository;

import com.microshop.model.Product;
import com.microshop.model.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Product> {}
