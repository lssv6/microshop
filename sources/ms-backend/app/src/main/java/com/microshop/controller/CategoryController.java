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

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CategoryDTO> findByFullName(
            @RequestParam(name = "full-name") String fullName,
            @RequestParam(name = "full-path") String fullPath) {
        boolean onlyOneParameterIsBlank = fullName.isBlank() ^ fullPath.isBlank();
        if (onlyOneParameterIsBlank) {
            if (!fullPath.isBlank()) {
                return ResponseEntity.of(categoryService.findByFullPath(fullPath));
            }
            return ResponseEntity.of(categoryService.findByFullName(fullName));
        }
        return ResponseEntity.of(
                        ProblemDetail.forStatusAndDetail(
                                HttpStatus.BAD_REQUEST,
                                "Must provide one of the avaliable filtering parameters: full-name"
                                        + " or full-path"))
                .build();
    }

    @GetMapping
    public ResponseEntity<CategoryDTO> findByFullPath(
            @RequestParam(name = "full-path", required = true) String fullPath) {
        return ResponseEntity.of(categoryService.findByFullPath(fullPath));
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

    @PostMapping
    public ResponseEntity<CategoryDTO> save(@Valid @RequestBody NewCategory newCategory) {
        CategoryDTO categoryDTO = categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }
}
