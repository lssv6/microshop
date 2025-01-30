package com.microshop.repository;

import com.microshop.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // public Optional<Category> findByName(String name);
    // public Optional<Category> findByPath(String path);
    public Optional<Category> findByFullPath(String fullPath);

    public Optional<Category> findByPrettyPath(String prettyPath);
}
