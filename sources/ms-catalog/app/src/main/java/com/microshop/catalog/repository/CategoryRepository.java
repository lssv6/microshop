package com.microshop.catalog.repository;

import com.microshop.catalog.model.Category;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    public Set<Category> findByParentIsNull();

    public Set<Category> findByName(String name);

    public Iterable<Category> findByParent(Category p);
}
