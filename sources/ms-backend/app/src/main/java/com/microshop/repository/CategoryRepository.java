package com.microshop.repository;

import com.microshop.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findByFullName(String fullName);

    public Optional<Category> findByFullPath(String fullPath);

    public Set<Category> findByParent(Category parent);
}
