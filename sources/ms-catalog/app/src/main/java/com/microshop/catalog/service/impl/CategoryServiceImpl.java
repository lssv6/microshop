package com.microshop.catalog.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microshop.catalog.model.Category;
import com.microshop.catalog.repository.CategoryRepository;
import com.microshop.catalog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }
    
    @Override
    public Iterable<Category> findAll(){
        return categoryRepository.findAll();
    }
    @Override
    public Category save(Category c){
        return categoryRepository.save(c);
    }

    @Override
    public Set<Category> findAllTopLevel(){
        return categoryRepository.findByParentIsNull();
    };

    @Override
    public Set<Category> findByName(String name){
        return categoryRepository.findByName(name);
    }
}

