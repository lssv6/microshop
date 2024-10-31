package com.microshop.catalog.service;

import com.microshop.catalog.model.Category;
import com.microshop.catalog.model.Product;
import java.util.Collection;

public interface ProductService {
  public Iterable<Product> findAll();

  public Product save(Product p);

  public Product findById(Long id);

  public Product findByNameLike(String likeString);

  public Collection<Product> findByCategory(Category category);
}
