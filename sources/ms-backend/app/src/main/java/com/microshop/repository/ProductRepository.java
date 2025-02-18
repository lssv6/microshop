package com.microshop.repository;

import com.microshop.model.Category;
import com.microshop.model.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findByName(String name);
    public Optional<Product> findByCode(Long code);
    public Page<Product> findByCategory(Category categoryId, Pageable pageable);
}
