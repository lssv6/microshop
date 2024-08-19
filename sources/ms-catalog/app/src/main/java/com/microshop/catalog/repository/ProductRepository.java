package com.microshop.catalog.repository;


import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microshop.catalog.model.Category;
import com.microshop.catalog.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    public Product findByNameLike(String likeString);

    public Set<Product> findByCategory(Category category);
}

