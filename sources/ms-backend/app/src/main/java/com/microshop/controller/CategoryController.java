package com.microshop.controller;

import com.microshop.dto.CategoryDTO;
import com.microshop.dto.NewCategoryDTO;
import com.microshop.dto.ProductDTO;
import com.microshop.service.CategoryService;
import com.microshop.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CategoryDTO> postNewCategory(@RequestBody NewCategoryDTO nCategoryDTO) {
        CategoryDTO categoryDTO = categoryService.create(nCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> postNewCategoryAsChild(
            @RequestBody NewCategoryDTO childCategoryDTO, @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.createNested(childCategoryDTO, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.of(categoryService.findById(id));
    }

    @GetMapping("/{id}/breadcrumb")
    public ResponseEntity<List<CategoryDTO>> getBreadcrumbs(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getBreadcrumb(id));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<Page<ProductDTO>> getProducts(@PathVariable Long id, Pageable pageable) {
        categoryService.findById(id).orElseThrow();
        return ResponseEntity.ok(productService.findByCategory(id, pageable));
    }
}
