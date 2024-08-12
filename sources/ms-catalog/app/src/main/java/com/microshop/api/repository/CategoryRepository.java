package com.microshop.api.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.microshop.api.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{
    
    public Set<Category> findByParentIsNull();

    public Set<Category> findByName(String name);

    public Iterable<Category> findByParent(Category p);
}

