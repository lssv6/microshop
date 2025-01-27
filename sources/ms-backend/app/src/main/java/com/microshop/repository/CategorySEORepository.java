package com.microshop.repository;

import com.microshop.model.CategorySEO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySEORepository extends JpaRepository<CategorySEO, Long> {
    public Optional<CategorySEO> findByCategoryId(Long categoryId);
}
