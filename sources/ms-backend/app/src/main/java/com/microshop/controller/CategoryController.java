package com.microshop.controller;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.request.NewCategory;
import com.microshop.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryDTO> findByFullNameOrFullPath(
            @RequestParam(name = "full-name", required = false) String fullName,
            @RequestParam(name = "full-path", required = false) String fullPath) {
        boolean onlyOneIsNull = fullName == null ^ fullPath == null;
        if (onlyOneIsNull) {
            if (fullPath != null) {
                return ResponseEntity.of(categoryService.findByFullPath(fullPath));
            }
            return ResponseEntity.of(categoryService.findByFullName(fullName));
        }
        return ResponseEntity.of(
                        ProblemDetail.forStatusAndDetail(
                                HttpStatus.BAD_REQUEST,
                                "Must provide only one of the avaliable filtering parameters:"
                                        + " full-name or full-path"))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.of(categoryService.findById(id));
    }

    @GetMapping("/{id}/breadcrumb")
    public ResponseEntity<List<CategoryDTO>> getBreadcrumbs(@PathVariable Long id) {
        List<CategoryDTO> breadcrumb = categoryService.getBreadcrumb(id);
        return ResponseEntity.ofNullable(breadcrumb);
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<Set<CategoryDTO>> getChildren(@PathVariable Long id) {
        Set<CategoryDTO> children = categoryService.getChildren(id);
        return ResponseEntity.ofNullable(children);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody NewCategory newCategory) {
        CategoryDTO categoryDTO = categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }
}
