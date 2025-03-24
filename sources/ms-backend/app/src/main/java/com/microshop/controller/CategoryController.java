package com.microshop.controller;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;
import com.microshop.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.of(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@RequestBody NewCategory newCategory) {
        CategoryDTO categoryDTO = categoryService.save(newCategory);
        return ResponseEntity.ofNullable(categoryDTO);
    }
}
