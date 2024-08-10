package com.microshop.api.repository;


import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microshop.api.model.Category;
import com.microshop.api.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{
    public Product findByNameLike(String likeString);

    public Set<Product> findByCategory(Category category);
}

