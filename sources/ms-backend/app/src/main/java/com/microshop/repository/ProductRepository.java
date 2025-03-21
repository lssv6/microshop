package com.microshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{}
