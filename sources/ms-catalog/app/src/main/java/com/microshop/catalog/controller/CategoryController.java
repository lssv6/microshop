package com.microshop.catalog.controller;

import com.microshop.catalog.dto.CategoryDTO;
import com.microshop.catalog.dto.NewCategoryDTO;
import com.microshop.catalog.exception.CategoryNotFoundException;
import com.microshop.catalog.model.Category;
import com.microshop.catalog.service.CategoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  @Autowired private ModelMapper mapper;

  @Autowired private CategoryService categoryService;

  ////////////////////////////////////////////////////////////////////////////
  // CREATE
  ////////////////////////////////////////////////////////////////////////////
  @PostMapping
  public CategoryDTO addCategory(@RequestBody NewCategoryDTO c) {
    Long parentId = c.getParentId();
    Category newCat = mapper.map(c, Category.class);
    // If parent is given.
    if (parentId != null) {
      // Then try to find the given parent
      Optional<Category> parent = categoryService.findById(parentId);
      // If the parent exists.
      // Then try to set the parent for the new category
      parent.ifPresentOrElse(
          p -> newCat.setParent(p),
          () -> {
            throw new CategoryNotFoundException("");
          });
    }
    categoryService.save(newCat);
    return mapper.map(newCat, CategoryDTO.class);
  }

  ////////////////////////////////////////////////////////////////////////////
  // READ
  ////////////////////////////////////////////////////////////////////////////
  @GetMapping("/{id}/children")
  public Iterable<CategoryDTO> getCategoryChildren(@PathVariable(name = "id") Long id) {
    Category category = categoryService.findById(id).get();
    Iterable<Category> children = category.getChildren();
    List<CategoryDTO> convertedChild = new ArrayList<CategoryDTO>();
    children.forEach(child -> convertedChild.add(mapper.map(child, CategoryDTO.class)));
    return convertedChild;
  }

  @GetMapping
  public Iterable<CategoryDTO> getCategories(
      @RequestParam(name = "only-top-level", defaultValue = "false", required = false)
          boolean onlyTopLevel) {
    Iterable<Category> cats =
        onlyTopLevel ? categoryService.findAllTopLevel() : categoryService.findAll();
    List<CategoryDTO> convertedCats = new ArrayList<CategoryDTO>();
    cats.forEach(c -> convertedCats.add(mapper.map(c, CategoryDTO.class)));

    return convertedCats;
  }

  @GetMapping("/{id}")
  public CategoryDTO getCategoryById(@PathVariable("id") Long id) {
    // Transform Category to CategoryDTO
    var cat =
        categoryService
            .findById(id)
            .orElseThrow(
                () -> new CategoryNotFoundException("Category id=%d not found".formatted(id)));
    return mapper.map(cat, CategoryDTO.class);
  }

  ////////////////////////////////////////////////////////////////////////////
  // UPDATE
  ////////////////////////////////////////////////////////////////////////////
  /**
   * This method changes the attribues of a category, only name and parent_id can be altered here.
   *
   * @param id
   * @param category
   */
  @PutMapping("/{id}")
  public CategoryDTO updateCategory(
      @PathVariable("id") Long id, @RequestBody CategoryDTO category) {
    Category cat =
        categoryService
            .findById(id)
            .orElseThrow(
                () -> new CategoryNotFoundException("Category id=%d not found".formatted(id)));
    mapper.map(category, cat); // Will copy all the fields to be changed. Eg. name.
    return mapper.map(categoryService.save(cat), CategoryDTO.class);
  }

  ////////////////////////////////////////////////////////////////////////////
  // DELETE
  ////////////////////////////////////////////////////////////////////////////
  // @DeleteMapping("/{id}")
  // public void hideCategory(@PathVariable("id") Long id){
  //    Category cat = categoryService.findById(id)
  //        .orElseThrow(() -> new CategoryNotFoundException("Category id=%d not
  // found".formatted(id)));
  //    cat.setHidden(true);
  //    categoryService.save(cat);
  // }
}
