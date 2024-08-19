package com.microshop.catalog.service;

import java.util.Optional;
import java.util.Set;


import com.microshop.catalog.model.Category;

public interface CategoryService{
    public Category save(Category c);
    public Optional<Category> findById(Long id);
    public Set<Category> findAllTopLevel();
    //public Iterable<Category> findChildren();
    public Iterable<Category> findAll();
    public Set<Category> findByName(String name);
    //public void setChild(Category parent, Category child);
}

